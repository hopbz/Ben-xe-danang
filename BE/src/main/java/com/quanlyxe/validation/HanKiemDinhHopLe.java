package com.quanlyxe.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = HanKiemDinhHopLeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HanKiemDinhHopLe {
    String message() default "Han kiem dinh khong dung, han kiem dinh phai lon hon thoi gian hien tai la 1 thang, xin hay nhap lai thong tin han kiem dinh";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
