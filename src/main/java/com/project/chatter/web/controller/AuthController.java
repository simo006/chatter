package com.project.chatter.web.controller;

import com.project.chatter.model.dto.RegisterUserDto;
import com.project.chatter.model.dto.UserDetailsDto;
import com.project.chatter.model.view.FieldErrorView;
import com.project.chatter.model.view.SuccessView;
import com.project.chatter.model.view.UserDataView;
import com.project.chatter.service.AuthService;
import com.project.chatter.web.exception.RequestBodyValidationError;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/test")
    public String isLogged() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return "Not logged!";
        }
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessView> register(@Valid @RequestBody RegisterUserDto registerUserDto,
                                                BindingResult bindingResult, HttpServletRequest request) throws ServletException {
        if (bindingResult.hasErrors()) {
            List<FieldErrorView> errors = bindingResult
                    .getFieldErrors().stream()
                    .map(violation -> mapError(violation.getField(), violation.getDefaultMessage()))
                    .toList();

            throw new RequestBodyValidationError(errors);
        }

        authService.register(registerUserDto);

        request.login(registerUserDto.getEmail(), registerUserDto.getPassword());

        UserDataView userDataView = new UserDataView(registerUserDto.getEmail(), registerUserDto.getFirstName(),
                registerUserDto.getLastName(), registerUserDto.getAge());

        return ResponseEntity.ok(okView("Successful registration", userDataView));
    }
}
