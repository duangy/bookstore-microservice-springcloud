package com.guangyao.bookstore.domain.validation;

import com.guangyao.bookstore.domain.client.ProductServiceClient;
import com.guangyao.bookstore.dto.Settlement;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SettlementValidator implements ConstraintValidator<SufficientStock, Settlement> {

    @Inject
    private ProductServiceClient service;

    @Override
    public boolean isValid(Settlement value, ConstraintValidatorContext context) {
        return value.getItems().stream().noneMatch(i -> service.queryStockpile(i.getProductId()).getAmount() < i.getAmount());
    }
}
