package com.project.chatter.model.view.basic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class ResponseView {

    private String timestamp;

    private int status;

    private String message;

    private String path;

    public ResponseView(int status, String message, String path) {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
