package com.project.chatter.service.impl;

import com.project.chatter.model.dto.RegisterUserDto;
import com.project.chatter.model.dto.UserDetailsDto;
import com.project.chatter.model.entity.User;
import com.project.chatter.repository.UserRepository;
import com.project.chatter.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));

        return mapUserToUserDetails(user);
    }

    private UserDetails mapUserToUserDetails(User user) {
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRole().name()))
                .toList();

        return new UserDetailsDto(user.getEmail(), user.getPassword(), authorities,
                user.getFirstName(), user.getLastName(), user.getAge());
    }
}
