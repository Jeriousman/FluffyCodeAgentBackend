package com.hojui.AgentIo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hojui.AgentIo.dto.LoginRequestDTO;
import com.hojui.AgentIo.service.AuthService;
// @Controller
@RestController 
public class LoginController {
    
    private final AuthService authService;
    
    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    // @GetMapping("/login")
    // public String loginPage() {
    //     return "login"; // @Controller를 사용하면 login이란 이름의 페이지로 이동하게 됨. 
    // }

    @PostMapping("/logging")
    public String logging(@RequestBody LoginRequestDTO loginRequest){
        return authService.jwtVerify(loginRequest);

        // return "Success";
    }

}
