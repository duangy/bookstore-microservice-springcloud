package com.guangyao.bookstore.domain.warehouse;

import com.guangyao.bookstore.domain.BaseEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Product extends BaseEntity {

    @NotEmpty(message = "商品名称不允许为空")
    private String title;

    @NotNull(message = "商品应当有明确的价格")
    @Min(value = 0, message = "商品价格最低为零")
    private Double price;

    @Min(value = 0, message = "评分最低为0")
    @Max(value = 10, message = "评分最高为10")
    private Float rate;

    private String description;

    private String cover;

    private String detail;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Set<Specification> specifications;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Set<Specification> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(Set<Specification> specifications) {
        this.specifications = specifications;
    }
}
