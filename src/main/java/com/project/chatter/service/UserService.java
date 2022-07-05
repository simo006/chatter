package com.project.chatter.service;

import java.util.List;

public interface UserService {

    void populateUsers();

    boolean isEmailFree(String email);

    List<String> getUserChatRooms(String email);

    List<String> getUserFriendsEmails(String email);
}
