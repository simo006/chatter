package com.project.chatter.web.exception;

import com.project.chatter.model.view.basic.ErrorView;
import com.project.chatter.web.exception.RequestBodyValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RequestBodyValidationError.class)
    public ErrorView handleRequestBodyValidationError(RequestBodyValidationError error, HttpServletRequest request) {
        return new ErrorView(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Invalid input data",
                request.getServletPath(),
                error.getErrors()
        );
    }

}
