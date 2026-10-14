package com.hojui.AgentIo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hojui.AgentIo.model.UserEntity;
import com.hojui.AgentIo.service.UserService;
// @Controller
@RestController 
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // @Controller를 사용하면 login이란 이름의 페이지로 이동하게 됨. 
    }

    @PostMapping("/logging")
    public String logging(@RequestBody UserEntity user){
        return UserService.JwtVerify();

        return "Success";
    }

}
