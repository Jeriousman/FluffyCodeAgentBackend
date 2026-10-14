package com.hojui.AgentIo.controller;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthController {

    @GetMapping("/")
    public String index()
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        System.out.println(username);
        System.out.println(role);
        return "index";
    }

    @GetMapping("/user")
    public String user()
    {
        return "user";
    }


    @GetMapping("/admin")
    public String admin()
    {
        return "admin";
    }
}
