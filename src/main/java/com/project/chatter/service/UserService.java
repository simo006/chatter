package com.project.chatter.service;

import com.project.chatter.model.dto.RegisterUserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    void populateUsers();

}
