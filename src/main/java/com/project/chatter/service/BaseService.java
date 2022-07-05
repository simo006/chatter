package com.project.chatter.service;

import com.project.chatter.model.entity.User;
import com.project.chatter.repository.UserRepository;

public interface BaseService {

    String getNames(String firstName, String lastName);

    User getCurrentUser(String userEmail, UserRepository userRepository);

    User getCurrentUser(UserRepository userRepository);
}
