package com.project.chatter.web.controller;

import com.project.chatter.model.dto.RegisterUserDto;
import com.project.chatter.model.view.UserDataView;
import com.project.chatter.model.view.basic.SuccessView;
import com.project.chatter.service.AuthService;
import com.project.chatter.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessView> register(@Valid @RequestBody RegisterUserDto registerUserDto,
                                                BindingResult bindingResult, HttpServletRequest request) throws ServletException {
        if (bindingResult.hasErrors()) {
            throwRequestBodyValidationError(bindingResult);
        }

        authService.register(registerUserDto);

        request.login(registerUserDto.getEmail(), registerUserDto.getPassword());

        UserDataView userDataView = new UserDataView(registerUserDto.getEmail(), registerUserDto.getFirstName(),
                registerUserDto.getLastName(), registerUserDto.getAge());

        return ResponseEntity.ok(okView("Successful registration", userDataView));
    }

    @GetMapping("/user-rooms")
    public ResponseEntity<SuccessView> getUserRooms(Principal principal) {
        List<String> userRooms = userService.getUserRooms(principal.getName());

        return ResponseEntity.ok(okView("User rooms", userRooms));
    }

    @GetMapping("/user-chat-rooms")
    public ResponseEntity<SuccessView> getUserChatRooms(Principal principal) {
        List<String> userChatRooms = userService.getUserChatRooms(principal.getName());

        return ResponseEntity.ok(okView("User chat rooms", userChatRooms));
    }
}
