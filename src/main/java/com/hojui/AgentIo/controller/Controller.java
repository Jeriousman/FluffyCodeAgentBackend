package com.hojui.AgentIo.controller;

import java.util.List;
import jakarta.validation.Valid;   // ← 추가
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hojui.AgentIo.dto.EffortChangeRequest;
import com.hojui.AgentIo.dto.LlmChangeRequest;
import com.hojui.AgentIo.dto.PermissionChangeRequest;
import com.hojui.AgentIo.dto.UserResponse;
import com.hojui.AgentIo.model.UserEntity;
import com.hojui.AgentIo.service.UserService;


@RestController
@RequestMapping("/api")
public class Controller {

    private final UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;
    }


    
    @GetMapping
    public String hello() {
        return "Welcome to fluffy cat agent world. You must feed thish cat very well everyday for good poo";
    }

    @GetMapping("/users/{userId}")
    public String hello(@PathVariable Integer userId) {
        System.out.println("Hello" + userId);
        return "Hello User: " + userId;
    }

    @GetMapping ("/users/{userId}/email")
    public String getUserEmail(@PathVariable Integer userId){
        return userService.fetchUserEmail(userId);
    }
    // 내 정보 조회: 토큰의 사용자만 반환하므로 남의 정보에 접근할 방법이 없다
    @GetMapping("/users/me")
    public UserResponse getMe(Authentication authentication) {
        return userService.getMe(authentication.getName());
    }

    @GetMapping("/users")          // ADMIN 전용 (SecurityConfig에서 제한)
    public List<UserResponse> getUsers() {
        return userService.fetchAllUsers();
    }
    // 작업제출

    // 1. Query a question to agent
    @PostMapping("/users/{userId}/sessions/{sessionId}/query")
    public String submitTextQuery(@RequestBody String inputText) {
        System.out.println("Input Text: " + inputText);
        return "Your input text is: " + inputText;
    }



    // 2. Set a model level to agent
    @PatchMapping ("/users/me/model")   // /users/{userId} 을 쓰지 않고 /users/me를 써서 유저아이디를 노출시키지 않음.
    public void submitChangeLlmModel(
        Authentication authentication,
        @Valid @RequestBody LlmChangeRequest request
    ) 
    {
        String username = authentication.getName();
        // Actual llm model change logic should be implemented here.
        userService.changeLlmModel(username, request.getLlmModel());
        // return "Model changed to: " + request.getLlmModel();
    }

    // Set permission mode to agent (auto or manual)
    @PatchMapping("/users/me/permission")
    public void submitChangePermissionMode(
        Authentication authentication,
        @Valid @RequestBody PermissionChangeRequest request
    )
    {
        String username = authentication.getName();
        userService.changePermissionMode(username, request.getPermissionMode());
    }


    // 3. Set effort level to agent
    @PatchMapping("/users/me/effort")
    public void submitChangeEffortMode(
        Authentication authentication,
        @Valid @RequestBody EffortChangeRequest request
    )
    {
        String username = authentication.getName();
        userService.changeEffortMode(username, request.getEffortMode());
        
    }

    // 4. Set a model among models in a list

    // 5. 



}
