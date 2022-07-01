package com.project.chatter.web.annotation.validator;

import com.project.chatter.service.AuthService;
import com.project.chatter.web.annotation.UniqueEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final AuthService authService;

    public UniqueEmailValidator(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email == null || email.isBlank() || authService.isEmailFree(email);
    }
}
