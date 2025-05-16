package com.bruce.auth.validator;

import com.bruce.auth.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isEmpty()){
            return false;
        }

        if (password.length() < 6 || password.length() > 20){
            return false;
        }

        if (!password.matches(".*[A-Z].*")){
            return false;
        }

        if (!password.matches(".*[a-z].*")){
            return false;
        }

        return password.matches(".*[^a-zA-Z0-9].*");
    }
}
