package com.project.chatter.service.impl;

import com.project.chatter.model.entity.Role;
import com.project.chatter.model.entity.User;
import com.project.chatter.model.enums.RoleType;
import com.project.chatter.repository.RoleRepository;
import com.project.chatter.repository.UserRepository;
import com.project.chatter.service.AuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;
    private AuthService authService;
    private User testUser;
    private Role userRole;


    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(userRepository, null, null, null);

        testUser = new User("test@email.com", "password", "Simeon", "Simeonov", 22);

        userRole = new Role(RoleType.USER);
        testUser.setRoles(List.of(userRole));
    }

    @Test
    void testLoadUserByUsernameShouldReturnTheCorrectUser() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

        UserDetails actual = authService.loadUserByUsername(testUser.getEmail());
        String[] actualAuthorities = actual.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

        Assertions.assertEquals(testUser.getEmail(), actual.getUsername());
        Assertions.assertEquals(testUser.getPassword(), actual.getPassword());
        Assertions.assertArrayEquals(new String[]{userRole.getRole().name()}, actualAuthorities);
    }

    @Test
    void testLoadUserShouldThrowWhenTheUserIsNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> authService.loadUserByUsername(testUser.getEmail()));
    }
}