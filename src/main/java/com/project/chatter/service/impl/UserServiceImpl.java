package com.project.chatter.service.impl;

import com.project.chatter.model.entity.Role;
import com.project.chatter.model.entity.User;
import com.project.chatter.model.enums.RoleType;
import com.project.chatter.repository.RoleRepository;
import com.project.chatter.repository.UserRepository;
import com.project.chatter.service.ChatService;
import com.project.chatter.service.UserService;
import com.project.chatter.web.exception.NotFoundError;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ChatService chatService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ChatService chatService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.chatService = chatService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void populateUsers() {
        if (userRepository.count() == 0) {
            List<User> users = List.of(
                    new User("s.simeonov@ue-varna.bg", passwordEncoder.encode("123456"), "Simeon", "Simeonov", 22),
                    new User("nikola.rashkov@ue-varna.bg", passwordEncoder.encode("123456"), "Nikola", "Rashkov", 25),
                    new User("ross.mitev@ue-varna.bg", passwordEncoder.encode("123456"), "Rostislav", "Mitev", 22)
            );

            Role role = roleRepository.findRoleByRole(RoleType.USER);
            List<Role> roles = List.of(role);
            users.forEach(user -> user.setRoles(roles));

            userRepository.saveAll(users);
        }
    }

    @Override
    public boolean isEmailFree(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public List<String> getUserRooms(String email) {
        return List.of(
                "/friends/status",
                "/friends/request"
        );
    }

    @Override
    @Transactional
    public List<String> getUserFriendsEmails(String email) {
        User currentUser = getCurrentUser(email, userRepository);

        List<String> userChatRooms = user.getChatRooms().stream()
                .map(r -> chatService.getChatRoomSubscriptionName(r.getId()))
                .toList();

        return userChatRooms;
    }
}
