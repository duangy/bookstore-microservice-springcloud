package com.guangyao.bookstore.dto;

import com.guangyao.bookstore.domain.warehouse.Product;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Map;

public class Settlement {

    @Size(min = 1, message = "结算单中缺少商品清单")
    private Collection<Item> items;

    @NotNull(message = "结算单中缺少配送信息")
    private Purchase purchase;

    public transient Map<Integer, Product> productMap;

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}
