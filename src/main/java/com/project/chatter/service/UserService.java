package com.project.chatter.service;

import java.util.List;

public interface UserService {

    void populateUsers();

    boolean isEmailFree(String email);

    List<String> getUserRooms(String email);

    List<String> getUserChatRooms(String email);
}
