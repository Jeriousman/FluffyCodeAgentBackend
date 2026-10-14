package com.hojui.AgentIo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hojui.AgentIo.dto.UserRequestDTO;
import com.hojui.AgentIo.service.UserService;

// @Controller 
@RestController 
public class UserSignUpController {
    private final UserService userService;

    public UserSignUpController(UserService userService) {
        this.userService = userService;
    }

    // Users sign up page to sign up here.
    @GetMapping("/signup")
    public String SignUpPage() {
        return "signup";
    }

    // The information given by users is sent to backend.
    @PostMapping("/signup")
    public String signUp(@RequestBody UserRequestDTO userRequestDTO) {
        userService.userSignUp(userRequestDTO);
        return "redirect:/"; // Redirect to login page after successful sign-up
    }

    
    
}
