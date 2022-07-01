package com.project.chatter.web.annotation;


import com.project.chatter.web.annotation.validator.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
@Documented
public @interface UniqueEmail {

    String message() default "email already taken";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
