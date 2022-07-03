package com.project.chatter.web.annotation.validator;

import com.project.chatter.service.UserService;
import com.project.chatter.web.annotation.UniqueEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService userService;

    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email == null || email.isBlank() || userService.isEmailFree(email);
    }
}
