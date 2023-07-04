package com.guangyao.bookstore.account.domain.validation;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
@Constraint(validatedBy = AccountValidation.UniqueAccountValidator.class)
public @interface UniqueAccount {

    String message() default "用户名称、邮箱、手机号码均不允许与现存用户重复";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payloads() default {};
}
