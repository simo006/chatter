package com.project.chatter.web.annotation;

import com.project.chatter.web.annotation.validator.EqualPasswordsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EqualPasswordsValidator.class)
@Documented
public @interface EqualPasswords {

    String message() default "passwords do not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}