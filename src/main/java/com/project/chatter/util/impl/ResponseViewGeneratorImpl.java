package com.project.chatter.util.impl;

import com.project.chatter.model.view.basic.SuccessView;
import com.project.chatter.util.ResponseViewGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResponseViewGeneratorImpl implements ResponseViewGenerator {

    @Override
    public SuccessView okView(String message, String baseUrl) {
        return new SuccessView(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                message,
                baseUrl
        );
    }

    @Override
    public SuccessView noContentView(String message, String baseUrl) {
        return new SuccessView(
                HttpStatus.NO_CONTENT.value(),
                HttpStatus.NO_CONTENT.getReasonPhrase(),
                message,
                baseUrl
        );
    }
}
