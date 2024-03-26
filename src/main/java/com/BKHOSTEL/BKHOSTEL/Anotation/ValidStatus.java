package com.BKHOSTEL.BKHOSTEL.Anotation;

import com.BKHOSTEL.BKHOSTEL.Validator.ValidEmailValidator;
import com.BKHOSTEL.BKHOSTEL.Validator.ValidStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Null;

import java.lang.annotation.*;

@Constraint(validatedBy = ValidStatusValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidStatus {
    String message() default "Status must me in : [\"SUCCESS\",\"FAILURE\",\"PENDING\",\"EXPIRED\"]";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
