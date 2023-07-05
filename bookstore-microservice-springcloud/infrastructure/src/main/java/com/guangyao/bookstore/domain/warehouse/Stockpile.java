package com.guangyao.bookstore.domain.warehouse;

import com.guangyao.bookstore.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Stockpile extends BaseEntity {

    private Integer amount;

    private Integer frozen;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private transient Product product;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getFrozen() {
        return frozen;
    }

    public void frozen(Integer frozen) {
        this.amount -= frozen;
        this.frozen += frozen;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void thawed(Integer number) {
        frozen(-1 * number);
    }

    public void decrease(Integer number) {
        this.frozen -= number;
    }

    public void increate(Integer number) {
        this.frozen += number;
    }
}
