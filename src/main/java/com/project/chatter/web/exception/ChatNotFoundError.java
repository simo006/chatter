package com.project.chatter.web.exception;

public class ChatNotFoundError extends NotFoundError{

    public ChatNotFoundError() {
        super("Chat not found");
    }
}
