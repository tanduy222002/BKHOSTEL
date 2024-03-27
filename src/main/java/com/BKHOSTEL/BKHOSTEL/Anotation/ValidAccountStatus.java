package com.BKHOSTEL.BKHOSTEL.Anotation;

import com.BKHOSTEL.BKHOSTEL.Validator.ValidAccountStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ValidAccountStatusValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidAccountStatus {
    String message() default "Status must me in : [\"ACTIVE\",\"BAN\"]";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
