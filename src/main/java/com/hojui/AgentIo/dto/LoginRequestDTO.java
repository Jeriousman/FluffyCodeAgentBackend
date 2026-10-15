package com.hojui.AgentIo.dto;

import lombok.Getter;
import lombok.Setter;


// 로그인 요청 시 클라이언트가 보내는 값 (username, password만 받음)
@Getter
@Setter
public class LoginRequestDTO {

    private String username;
    private String password;
}
