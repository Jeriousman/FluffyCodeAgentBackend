package com.hojui.AgentIo.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hojui.AgentIo.dto.UserRequest;
import com.hojui.AgentIo.model.EffortMode;
import com.hojui.AgentIo.model.LlmMode;
import com.hojui.AgentIo.model.PermissionMode;
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



    /// 
    /// 
    /// 
    /// 
    ///  USER's PERMISSION, MODEL, EFFORT Change
    /// 
    /// 
    /// 
    ///////////////////////
    // user llm model change update
    public void changeLlmModel(String username, LlmMode modelName) {
        // Logic to change the LLM model for the user (not yet implemented)
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        user.setLlmModel(modelName);
        userRepository.save(user);
    }

    // user llm permission update
    public void changePermissionMode(String username, PermissionMode permissionMode) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        user.setPermissionMode(permissionMode);
        userRepository.save(user);
    }


    public void changeEffortMode(String username, EffortMode effortMode) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        user.setEffort(effortMode);
        userRepository.save(user);

    }

    ///////////////////////
    /// 
    /// 
    /// 
    /// 
    /// 
    /// 






    public String fetchUserEmail(Integer userId){
        UserEntity userInfo = userRepository.findById(userId).orElse(new UserEntity());
        return userInfo.getEmail();
    }


    // user sing up method
    public void userSignUp(UserRequest userRequest) {
        
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();
        String email = userRequest.getEmail();

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
