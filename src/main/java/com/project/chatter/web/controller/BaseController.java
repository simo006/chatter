package com.project.chatter.web.controller;

import com.project.chatter.model.view.basic.FieldErrorView;
import com.project.chatter.model.view.basic.SuccessView;
import com.project.chatter.web.exception.RequestBodyValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BaseController {

    private HttpServletRequest request;

    protected FieldErrorView mapError(String field, String message) {
        return new FieldErrorView(field, message);
    }

    protected void throwRequestBodyValidationError(BindingResult bindingResult) {
        List<FieldErrorView> errors = bindingResult
                .getFieldErrors().stream()
                .map(violation -> mapError(violation.getField(), violation.getDefaultMessage()))
                .toList();

        throw new RequestBodyValidationError(errors);
    }

    protected SuccessView okView(String message, Object additionalData) {
        return new SuccessView(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                message,
                request.getServletPath(),
                additionalData
        );
    }

    protected SuccessView okView(String message) {
        return new SuccessView(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                message,
                request.getServletPath()
        );
    }

    @Autowired
    private BaseController setRequest(HttpServletRequest request) {
        this.request = request;
        return this;
    }
}
