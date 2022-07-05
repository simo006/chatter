package com.project.chatter.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.chatter.model.enums.StatusType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusChangeDto {

    @NotNull
    private StatusType status;

    private String sendTo;

    @NotNull
    @Email
    private String senderEmail;

    public StatusType getStatus() {
        return status;
    }

    public StatusChangeDto setStatus(StatusType status) {
        this.status = status;
        return this;
    }

    public String getSendTo() {
        return sendTo;
    }

    public StatusChangeDto setSendTo(String sendTo) {
        this.sendTo = sendTo;
        return this;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public StatusChangeDto setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
        return this;
    }
}
