package com.guangyao.bookstore.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
@Constraint(validatedBy = SettlementValidator.class)
public @interface SufficientStock {
    String message() default "商品库存不足";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
