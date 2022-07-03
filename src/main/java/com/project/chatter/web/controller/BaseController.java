package com.project.chatter.web.controller;

import com.project.chatter.model.view.basic.FieldErrorView;
import com.project.chatter.model.view.basic.SuccessView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    private HttpServletRequest request;

    protected FieldErrorView mapError(String field, String message) {
        return new FieldErrorView(field, message);
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
