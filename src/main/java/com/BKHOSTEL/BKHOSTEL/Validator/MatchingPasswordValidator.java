package com.BKHOSTEL.BKHOSTEL.Validator;

import com.BKHOSTEL.BKHOSTEL.Anotation.MatchingPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class MatchingPasswordValidator implements ConstraintValidator<MatchingPassword, Object> {
    private String password;
    private String confirmPassword;

    @Override
    public void initialize(MatchingPassword matching) {
        this.password = matching.password();
        this.confirmPassword = matching.confirmPassword();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(password);
        Object confirmPasswordValue = new BeanWrapperImpl(value).getPropertyValue(confirmPassword);

        return (passwordValue != null) ? passwordValue.equals(confirmPasswordValue) : false;
    }


}
