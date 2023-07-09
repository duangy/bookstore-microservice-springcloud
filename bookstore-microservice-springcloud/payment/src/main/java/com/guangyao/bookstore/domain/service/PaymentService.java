package com.guangyao.bookstore.domain.service;

import com.guangyao.bookstore.domain.Payment;
import com.guangyao.bookstore.domain.client.ProductServiceClient;
import com.guangyao.bookstore.domain.repository.PaymentRepository;
import com.guangyao.bookstore.dto.Settlement;
import com.guangyao.bookstore.infrastructure.cache.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

@Named
public class PaymentService {

    private static final long DEFAULT_PRODUCT_FROZEN_EXPIRES = CacheConfiguration.SYSTEM_DEFAULT_EXPIRES / 2;

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final Timer timer = new Timer();

    @Inject
    private ProductServiceClient stockpileService;

    @Inject
    private PaymentRepository paymentRepository;

    @Resource(name = "settlement")
    private Cache settlementCache;

    public Payment producePayment(Settlement bill) {
        double total = bill.getItems().stream().mapToDouble(i -> {
            stockpileService.frozen(i.getProductId(), i.getAmount());
            return bill.productMap.get(i.getProductId()).getPrice() * i.getAmount();
        }).sum() + 12;
        Payment payment = new Payment(total, DEFAULT_PRODUCT_FROZEN_EXPIRES);
        paymentRepository.save(payment);
        settlementCache.put(payment.getPayId(), bill);
        log.info("创建支付订单，总额：{}", payment.getTotalPrice());
        return payment;
    }

    public double accomplish(String payId) {
        synchronized (payId.intern()) {
            Payment payment = paymentRepository.getByPayId(payId);
            if (payment.getPayState() == Payment.State.WAITING) {
                payment.setPayState(Payment.State.PAYED);
                paymentRepository.save(payment);
                accomplishSettlement(Payment.State.PAYED, payment.getPayId());
                log.info("编号为{}的支付单已处理完成，等待支付", payId);
                return payment.getTotalPrice();
            } else {
                throw new UnsupportedOperationException("当前订单不允许支付，当前状态为：" + payment.getPayState());
            }
        }
    }

    public void cancel(String payId) {
        synchronized (payId.intern()) {
            Payment payment = paymentRepository.getByPayId(payId);
            if (payment.getPayState() == Payment.State.WAITING) {
                payment.setPayState(Payment.State.CANCEL);
                paymentRepository.save(payment);
                accomplishSettlement(Payment.State.CANCEL, payment.getPayId());
                log.info("编号为{}的支付单已被取消", payId);
            } else {
                throw new UnsupportedOperationException("当前订单不允许取消，当前状态为：" + payment.getPayState());
            }
        }
    }

    private void accomplishSettlement(Payment.State endState, String payId) {
        Settlement settlement = (Settlement) Objects.requireNonNull(Objects.requireNonNull(settlementCache.get(payId)).get());
        settlement.getItems().forEach(i -> {
            if (endState == Payment.State.PAYED) {
                stockpileService.decrease(i.getProductId(), i.getAmount());
            } else {
                stockpileService.thawed(i.getProductId(), i.getAmount());
            }
        });
    }

    public void setupAutoThawedTrigger(Payment payment) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (payment.getPayId().intern()) {
                    Payment currentPayment = paymentRepository.findById(payment.getId()).orElseThrow(() -> new EntityNotFoundException(
                            payment.getId().toString()));
                    if (currentPayment.getPayState() == Payment.State.WAITING) {
                        log.info("支付单{}当前状态为：WAITING，转变为：TIMEOUT", payment.getId());
                        accomplishSettlement(Payment.State.TIMEOUT, payment.getPayId());
                    }
                }
            }
        }, payment.getExpires());
    }

    public void rollbackSettlement(Payment.State endState, String payId) {
        Settlement settlement = (Settlement) Objects.requireNonNull(Objects.requireNonNull(settlementCache.get(payId)).get());
        settlement.getItems().forEach(i -> {
            if (endState == Payment.State.PAYED) {
                stockpileService.increase(i.getProductId(), i.getAmount());
            } else {
                stockpileService.frozen(i.getProductId(), i.getAmount());
            }
        });
    }
}
