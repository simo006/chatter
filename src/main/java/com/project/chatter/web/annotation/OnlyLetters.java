package com.project.chatter.web.annotation;

import com.project.chatter.web.annotation.validator.OnlyLettersValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OnlyLettersValidator.class)
@Documented
public @interface OnlyLetters {

    String message() default "only letters expected";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
