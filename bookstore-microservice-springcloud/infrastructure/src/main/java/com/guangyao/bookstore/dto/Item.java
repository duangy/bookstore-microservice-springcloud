package com.guangyao.bookstore.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Item {

    @NotNull(message = "结算单中必须有明确的商品数量")
    @Min(value = 1, message = "结算单中商品数量至少为一件")
    private Integer amount;

    @JsonProperty("id")
    @NotNull(message = "结算单中必须有明确的商品信息")
    private Integer productId;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
