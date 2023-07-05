package com.guangyao.bookstore.domain;

import com.guangyao.bookstore.domain.account.Account;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Entity;
import java.util.Date;
import java.util.UUID;

@Entity
public class Payment extends BaseEntity {

    public enum State {
        WAITING,
        CANCEL,
        PAYED,
        TIMEOUT
    }

    private Date createTime;

    private String payId;

    private Double totalPrice;

    private Long expires;

    private String paymentLink;

    private State payState;

    public Payment() {}

    public Payment(Double totalPrice, Long expires) {
        setTotalPrice(totalPrice);
        setExpires(expires);
        setCreateTime(new Date());
        setPayState(State.WAITING);
        setPayId(UUID.randomUUID().toString());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Account) {
            setPaymentLink("/pay/modify/" + getPayId() + "?state=PAYED&accountId=" + ((Account) principal).getId());
        } else {
            setPaymentLink("/pay/modify/" + getPayId() + "?state=PAYED");
        }
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public String getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }

    public State getPayState() {
        return payState;
    }

    public void setPayState(State payState) {
        this.payState = payState;
    }
}
