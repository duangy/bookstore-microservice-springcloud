package com.guangyao.bookstore.application;

import com.guangyao.bookstore.domain.Payment;
import com.guangyao.bookstore.domain.client.ProductServiceClient;
import com.guangyao.bookstore.domain.service.PaymentService;
import com.guangyao.bookstore.domain.service.WalletService;
import com.guangyao.bookstore.dto.Settlement;
import org.springframework.cache.Cache;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Transactional
public class PaymentApplicationService {

    @Inject
    private PaymentService paymentService;

    @Inject
    private WalletService walletService;

    @Inject
    private ProductServiceClient productService;

    @Resource(name = "settlement")
    private Cache settlementCache;

    public Payment executeBySettlement(Settlement bill) {
        productService.replenishProductInformation(bill);
        Payment payment = paymentService.producePayment(bill);
        paymentService.setupAutoThawedTrigger(payment);
        return payment;
    }

    public void accomplishPayment(Integer accountId, String payId) {
        double price = paymentService.accomplish(payId);
        try{
            walletService.decrease(accountId, price);
        } catch (Throwable e) {
            paymentService.rollbackSettlement(Payment.State.PAYED, payId);
            throw e;
        }
        settlementCache.evict(payId);
    }

    public void cancelPayment(String payId) {
        paymentService.cancel(payId);
        settlementCache.evict(payId);
    }
}
