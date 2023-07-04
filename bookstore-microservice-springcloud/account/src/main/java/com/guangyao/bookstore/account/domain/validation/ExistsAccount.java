package com.guangyao.bookstore.account.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
@Constraint(validatedBy = AccountValidation.ExistsAccountValidator.class)
public @interface ExistsAccount {
    String message() default "用户不存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
