package com.BKHOSTEL.BKHOSTEL.Validator;

import com.BKHOSTEL.BKHOSTEL.Anotation.ValidPostStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ValidStatusValidator implements ConstraintValidator<ValidPostStatus, String> {
    @Override
    public boolean isValid(String status, ConstraintValidatorContext constraintValidatorContext) {
        String regexPattern = "^(PENDING|SUCCESS|FAILURE|EXPIRED)$";
        return status.matches(regexPattern);

    }
}
