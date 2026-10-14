package com.hojui.AgentIo.dto;

import lombok.Getter;
import lombok.Setter;


@Getter 
@Setter 
public class UserRequestDTO {
    
    private String username;
    private String password;
    private String email;
    private String createdAt;
}
