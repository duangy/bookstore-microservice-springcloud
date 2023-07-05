package com.guangyao.bookstore.warehouse.domain;

import com.guangyao.bookstore.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Advertisement extends BaseEntity {

    @NotEmpty(message = "广告图片不允许为空")
    private String image;

    @NotNull(message = "广告应当有关联的商品")
    @Column(name = "product_id")
    private Integer productId;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
