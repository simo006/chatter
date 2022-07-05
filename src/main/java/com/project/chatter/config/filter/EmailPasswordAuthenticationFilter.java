package com.project.chatter.config.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.chatter.model.dto.EmailAndPasswordDto;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmailPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationProvider authenticationProvider;

    public EmailPasswordAuthenticationFilter(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            // Extract user email and password from a json object
            EmailAndPasswordDto userData = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(request.getInputStream(), EmailAndPasswordDto.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(userData.getEmail(), userData.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return authenticationProvider.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
