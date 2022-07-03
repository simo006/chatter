package com.project.chatter.model.dto;

import javax.validation.constraints.NotBlank;

public class SendMessageDto {

    @NotBlank
    private String message;

    public String getMessage() {
        return message;
    }

    public SendMessageDto setMessage(String message) {
        this.message = message;
        return this;
    }
}
