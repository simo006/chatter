package com.project.chatter.config;

import com.project.chatter.service.ChatService;
import com.project.chatter.service.RoleService;
import com.project.chatter.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;
    private final ChatService chatService;

    public DatabaseSeeder(RoleService roleService, UserService userService, ChatService chatService) {
        this.roleService = roleService;
        this.userService = userService;
        this.chatService = chatService;
    }

    @Override
    public void run(String... args) {
//        roleService.populateRoles();
//        userService.populateUsers();
//        chatService.populateChatRoomAndMessages();
    }
}
