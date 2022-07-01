package com.project.chatter.model.view;

public class FieldErrorView {

    private String field;
    private String message;

    public FieldErrorView(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
