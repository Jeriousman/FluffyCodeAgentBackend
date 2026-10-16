package com.hojui.AgentIo.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.hojui.AgentIo.dto.LoginRequest;

// 로그인 검사 + JWT 발급 담당.
// AuthenticationManager는 UserService(UserDetailsService)를 사용해서 만들어지기 때문에
// UserService 안에서 AuthenticationManager를 주입받으면 순환 참조가 생긴다. 그래서 별도 클래스로 분리.
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // JWT authentication
    public String jwtVerify(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if(authentication.isAuthenticated())
            return jwtService.generateToken(loginRequest.getUsername());

        return "Fail";
    }
}
