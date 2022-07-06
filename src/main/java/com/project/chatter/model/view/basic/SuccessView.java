package com.project.chatter.model.view.basic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessView extends ResponseView {

    private final String success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("data")
    private Object additionalData;

    public SuccessView(int status, String success, String message, String path, Object additionalData) {
        super(status, message, path);
        this.success = success;
        this.additionalData = additionalData;
    }

    public SuccessView(int status, String success, String message, String path) {
        super(status, message, path);
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public Object getAdditionalData() {
        return additionalData;
    }
}
