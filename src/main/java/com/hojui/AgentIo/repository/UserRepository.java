package com.hojui.AgentIo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hojui.AgentIo.model.UserEntity;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    // Additional query methods can be defined here if needed){

    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    Optional<UserEntity> findByUsername(String username);

    
}
