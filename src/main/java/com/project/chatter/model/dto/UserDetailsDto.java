package com.project.chatter.model.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetailsDto extends User {

    private final String firstName;
    private final String lastName;
    private final Integer age;

    public UserDetailsDto(String email, String password, Collection<? extends GrantedAuthority> authorities, String firstName, String lastName, Integer age) {
        super(email, password, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getEmail() {
        return super.getUsername();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }
}
