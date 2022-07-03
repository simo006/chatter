package com.project.chatter.web.controller;

import com.project.chatter.model.dto.RegisterUserDto;
import com.project.chatter.model.view.basic.FieldErrorView;
import com.project.chatter.model.view.basic.SuccessView;
import com.project.chatter.model.view.UserDataView;
import com.project.chatter.service.AuthService;
import com.project.chatter.web.exception.RequestBodyValidationError;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    private final AuthService userService;

    public AuthController(AuthService userService) {
        this.userService = userService;
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
            throwRequestBodyValidationError(bindingResult);
        }

        userService.register(registerUserDto);

        request.login(registerUserDto.getEmail(), registerUserDto.getPassword());

        UserDataView userDataView = new UserDataView(registerUserDto.getEmail(), registerUserDto.getFirstName(),
                registerUserDto.getLastName(), registerUserDto.getAge());

        return ResponseEntity.ok(okView("Successful registration", userDataView));
    }
}
