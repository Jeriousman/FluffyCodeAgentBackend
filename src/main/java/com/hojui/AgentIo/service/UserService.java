package com.hojui.AgentIo.service;

import org.springframework.stereotype.Service;

import com.hojui.AgentIo.model.User;
import com.hojui.AgentIo.repository.UsersRepo;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor
public class UserService {

    private final UsersRepo usersRepo;

    public void changeLlmModel(Integer userId, String modelName) {
        // Logic to change the LLM model for the user (not yet implemented)
        System.out.println("Changing LLM model for user " + userId + " to " + modelName);
    }

    public String fetchUserEmail(Integer userId){
        User userInfo = usersRepo.findById(userId).orElse(new User());
        return userInfo.getEmail();
    }

}
