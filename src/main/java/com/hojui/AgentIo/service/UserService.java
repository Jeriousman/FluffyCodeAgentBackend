package com.hojui.AgentIo.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hojui.AgentIo.dto.UserRequestDTO;
import com.hojui.AgentIo.model.UserEntity;
import com.hojui.AgentIo.model.UserRole;
import com.hojui.AgentIo.repository.UserRepository;

@Service 
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void changeLlmModel(Integer userId, String modelName) {
        // Logic to change the LLM model for the user (not yet implemented)
        System.out.println("Changing LLM model for user " + userId + " to " + modelName);
    }

    public String fetchUserEmail(Integer userId){
        UserEntity userInfo = userRepository.findById(userId).orElse(new UserEntity());
        return userInfo.getEmail();
    }


    // user sing up method
    public void userSignUp(UserRequestDTO userRequestDTO) {
        
        String username = userRequestDTO.getUsername();
        String password = userRequestDTO.getPassword();
        String email = userRequestDTO.getEmail();

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Encrypt the password before saving
        user.setRole(UserRole.USER); // Set default role
        user.setEmail(email);

        userRepository.save(user);

        // 이미 동일한 아이디가 존재하는지 등에 대한 추가 기능 개발
    }

    // Fetch user information from DB then build the info into Spring User object (This is automatically called in formLogin in SecurityConfig)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        UserEntity user = userRepository.findByUsername(username).orElseThrow();

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    public List<UserEntity> fetchAllUsers() {
        return userRepository.findAll();
    }



}
