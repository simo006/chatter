package com.project.chatter.model.view.basic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class ResponseView {

    private final String timestamp;

    private final int status;

    private final String message;

    private final String path;

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
