package com.hojui.AgentIo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hojui.AgentIo.dto.LlmChangeRequest;
import com.hojui.AgentIo.dto.PermissionChangeRequest;
import com.hojui.AgentIo.service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Controller {

    private final UserService userService;


    
    @GetMapping
    public String hello() {
        return "Welcome to fluffy cat agent world. You must feed thish cat very well everyday for good poo";
    }

    @GetMapping("/users/{userId}")
    public String hello(@PathVariable Integer userId) {
        System.out.println("Hello" + userId);
        return "Hello User: " + userId;
    }

    @GetMapping("/users/{userId}/sessions")
    // Get all the sessions for user to choose
    public String getResponse(){
        // System.out.println("Input Text: " + inputText);
        return "Your session ID is: Meh LOL";
    }

    @GetMapping ("/users/{userId}/email")
    public String getUserEmail(@PathVariable Integer userId){
        return userService.fetchUserEmail(userId);
    }









    // 작업제출

    // 1. Query a question to agent
    @PostMapping("/users/{userId}/sessions/{sessionId}/query")
    public String submitTextQuery(@RequestBody String inputText) {
        System.out.println("Input Text: " + inputText);
        return "Your input text is: " + inputText;
    }



    // 2. Set effort level to agent
    @PatchMapping ("/users/{userId}/llm-model")
    public String submitChangeLlmModel(
        @PathVariable("userId") Integer userId,
        @RequestBody LlmChangeRequest request
    ) 
    {
        // Actual llm model change logic should be implemented here.
        userService.changeLlmModel(userId, request.getModelName());
        return "Model changed to: " + request.getModelName();
 
    }

    // 3. Set permission mode to agent (auto or manual)

    @PatchMapping("/users/{userId}/permission-mode")
    public String submitChangePermissionMode(
        @PathVariable ("userId") String userId,
        @RequestBody PermissionChangeRequest request
    )
    {
        return request.getPermissionMode().toString();

    }

    // 4. Set a model among models in a list

    // 5. 



}
