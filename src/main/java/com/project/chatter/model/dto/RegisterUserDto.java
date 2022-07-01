package com.project.chatter.model.dto;

import com.project.chatter.web.annotation.EqualPasswords;
import com.project.chatter.web.annotation.OnlyLetters;
import com.project.chatter.web.annotation.Password;
import com.project.chatter.web.annotation.UniqueEmail;

import javax.validation.constraints.*;

@EqualPasswords
public class RegisterUserDto {

    @NotBlank
    @Email
    @UniqueEmail
    private String email;

    @NotBlank
    @Size(min = 5)
    @Password
    private String password;

    @NotBlank
    @Size(min = 5)
    private String confirmPassword;

    @NotBlank
    @Size(min = 5, max = 20)
    @OnlyLetters
    private String firstName;

    @NotBlank
    @Size(min = 5, max = 20)
    @OnlyLetters
    private String lastName;

    @Min(0)
    @Max(120)
    @NotNull
    private Integer age;

    public String getEmail() {
        return email;
    }

    public RegisterUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public RegisterUserDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public RegisterUserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public RegisterUserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public RegisterUserDto setAge(Integer age) {
        this.age = age;
        return this;
    }
}
