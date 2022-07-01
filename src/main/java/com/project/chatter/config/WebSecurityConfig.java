package com.project.chatter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.chatter.config.filter.EmailPasswordAuthenticationFilter;
import com.project.chatter.model.dto.UserDetailsDto;
import com.project.chatter.model.view.SuccessView;
import com.project.chatter.model.view.UserDataView;
import com.project.chatter.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig implements WebMvcConfigurer {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(AuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                    .mvcMatchers("/auth/register", "/auth/test").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .addFilterBefore(emailPasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .formLogin().loginProcessingUrl("/auth/login").permitAll()
                .and()
                    .logout().logoutUrl("/auth/logout").permitAll()
                    .logoutSuccessHandler(getLogoutSuccessHandler())
                .and()
                    .cors()
                .and()
                    .csrf()
                    .disable();

        return http.build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowCredentials(true);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authService);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setHideUserNotFoundExceptions(false);

        return provider;
    }

    private EmailPasswordAuthenticationFilter emailPasswordAuthenticationFilter() {
        EmailPasswordAuthenticationFilter authenticationFilter = new EmailPasswordAuthenticationFilter(authenticationProvider());
        authenticationFilter.setFilterProcessesUrl("/auth/login");
        authenticationFilter.setAuthenticationFailureHandler(getAuthenticationFailureHandler());
        authenticationFilter.setAuthenticationSuccessHandler(getAuthenticationSuccessHandler());

        return authenticationFilter;
    }

    // request handlers
    private AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return (request, response, exception) ->
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
    }

    private AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            PrintWriter out = response.getWriter();
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            UserDetailsDto principal = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDataView userDataView = new UserDataView(principal.getEmail(), principal.getFirstName(),
                    principal.getLastName(), principal.getAge());

            SuccessView successView = new SuccessView(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    "Successful login.",
                    request.getServletPath(),
                    userDataView
            );
            String json = new ObjectMapper().writeValueAsString(successView);

            out.print(json);
            out.flush();
        };
    }

    private LogoutSuccessHandler getLogoutSuccessHandler() {
        return (request, response, authentication) ->
                response.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
