package com.project.chatter.model.dto;

public class EmailAndPasswordDto {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public EmailAndPasswordDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public EmailAndPasswordDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
