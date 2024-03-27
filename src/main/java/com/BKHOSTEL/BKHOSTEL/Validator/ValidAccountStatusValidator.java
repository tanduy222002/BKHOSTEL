package com.BKHOSTEL.BKHOSTEL.Validator;

import com.BKHOSTEL.BKHOSTEL.Anotation.ValidAccountStatus;
import com.BKHOSTEL.BKHOSTEL.Anotation.ValidPostStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ValidAccountStatusValidator implements ConstraintValidator<ValidAccountStatus, String> {
    @Override
    public boolean isValid(String status, ConstraintValidatorContext constraintValidatorContext) {
        String regexPattern = "^(ACTIVE|BAN)$";
        return status.matches(regexPattern);

    }
}
