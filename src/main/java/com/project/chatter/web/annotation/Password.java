package com.project.chatter.web.annotation;

import com.project.chatter.web.annotation.validator.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {

    String message() default "password contains invalid symbols";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
