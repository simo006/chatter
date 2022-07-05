package com.project.chatter.service.impl;

import com.project.chatter.model.dto.UserDetailsDto;
import com.project.chatter.model.entity.User;
import com.project.chatter.repository.UserRepository;
import com.project.chatter.service.BaseService;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseServiceImpl implements BaseService {

    @Override
    public String getNames(String firstName, String lastName) {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public User getCurrentUser(String userEmail, UserRepository userRepository) {
        return userRepository.findByEmail(userEmail).get();
    }

    @Override
    public User getCurrentUser(UserRepository userRepository) {
        UserDetailsDto principal = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return getCurrentUser(principal.getEmail(), userRepository);
    }
}
