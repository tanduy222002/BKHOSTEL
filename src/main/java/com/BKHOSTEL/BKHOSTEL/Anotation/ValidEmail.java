package com.BKHOSTEL.BKHOSTEL.Anotation;

import com.BKHOSTEL.BKHOSTEL.Validator.ValidEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.*;

@Constraint(validatedBy = ValidEmailValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidEmail {
    String message() default "This is a invalid email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
