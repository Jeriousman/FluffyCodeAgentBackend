package com.hojui.AgentIo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hojui.AgentIo.model.User;

public interface UsersRepo extends JpaRepository<User, Integer> {
    // Additional query methods can be defined here if needed){
    
}
