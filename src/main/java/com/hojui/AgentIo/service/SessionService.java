package com.hojui.AgentIo.service;

import org.springframework.stereotype.Service;

import com.hojui.AgentIo.model.SessionEntity;
import com.hojui.AgentIo.model.UserEntity;
import com.hojui.AgentIo.repository.SessionRepository;

/**
 * SessionService
 */
@Service 
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionEntity sessionEntity;



    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void GetSession(String username, ){
        sessionRepository.findByPublicIdAndUser_Username(username, sessionEntity.getPublicId());


        }
    

}
