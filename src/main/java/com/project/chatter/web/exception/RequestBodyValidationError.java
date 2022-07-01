package com.project.chatter.web.exception;

import com.project.chatter.model.view.FieldErrorView;

import java.util.List;

public class RequestBodyValidationError extends RuntimeException {

    private final List<FieldErrorView> errors;

    public RequestBodyValidationError(List<FieldErrorView> errors) {
        this.errors = errors;
    }

    public List<FieldErrorView> getErrors() {
        return errors;
    }
}
