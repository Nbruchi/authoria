package com.bruce.auth.annotation;

import com.bruce.auth.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {
    String message() default "Invalid password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
