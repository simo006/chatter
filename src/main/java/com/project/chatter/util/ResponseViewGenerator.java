package com.project.chatter.util;

import com.project.chatter.model.view.basic.SuccessView;

public interface ResponseViewGenerator {

    SuccessView okView(String message, String baseUrl);

    SuccessView noContentView(String message, String baseUrl);
}
