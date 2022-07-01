package com.project.chatter.web.annotation.validator;

import com.project.chatter.model.dto.RegisterUserDto;
import com.project.chatter.web.annotation.EqualPasswords;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualPasswordsValidator implements ConstraintValidator<EqualPasswords, Object> {

    private String message;

    @Override
    public void initialize(EqualPasswords constraintAnnotation) {
        this.message = constraintAnnotation.message();

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object model, ConstraintValidatorContext context) {
        RegisterUserDto registerUserDto = (RegisterUserDto) model;
        boolean isValid = registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }

        return isValid;
    }
}
