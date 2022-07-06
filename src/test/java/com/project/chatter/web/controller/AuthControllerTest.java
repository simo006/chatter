package com.project.chatter.web.controller;

import com.project.chatter.model.entity.ChatRoom;
import com.project.chatter.model.entity.Role;
import com.project.chatter.model.entity.User;
import com.project.chatter.model.enums.ChatRoomType;
import com.project.chatter.model.enums.RoleType;
import com.project.chatter.repository.ChatRoomRepository;
import com.project.chatter.repository.RoleRepository;
import com.project.chatter.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    private User testUser;

    private static final String EMAIL = "email@example.com";

    @BeforeEach
    void setUp() {
        testUser = new User(EMAIL, "password", "Simeon", "Simeonov", 22);

        List<Role> roles = List.of(
                new Role(RoleType.USER),
                new Role(RoleType.ADMINISTRATOR)
        );

        roleRepository.saveAll(roles);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        chatRoomRepository.deleteAll();
    }

    @Test
    @WithMockUser(EMAIL)
    void testGetUserChatRoomsShouldReturnEmptyListWhenThereIsNoChatRooms() throws Exception {
        userRepository.save(testUser);

        mockMvc.perform(get("/auth/user-chat-rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    @WithMockUser(EMAIL)
    @Transactional
    void testGetUserChatRoomsShouldReturnTheCorrectRoomsWhenThereIsOneRoom() throws Exception {
        ChatRoom chatRoom = new ChatRoom(ChatRoomType.PERSONAL, List.of(testUser), new ArrayList<>());
        chatRoomRepository.save(chatRoom);

        testUser.setChatRooms(List.of(chatRoom));
        userRepository.save(testUser);

        mockMvc.perform(get("/auth/user-chat-rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0]", is("/chat-room/1/messages")));
    }

    @Test
    void testGetUserChatRoomsShouldThrowWhenTheUserIsNotLoggedIn() throws Exception {
        mockMvc.perform(get("/auth/user-chat-rooms"))
                .andExpect(status().isForbidden());
    }
}