package com.quanlyxe.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class HanKiemDinhHopLeValidator implements ConstraintValidator<HanKiemDinhHopLe, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return !value.isBefore(LocalDate.now().plusMonths(1));
    }
}
