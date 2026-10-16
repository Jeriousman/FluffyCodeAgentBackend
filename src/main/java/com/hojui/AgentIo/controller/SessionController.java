package com.hojui.AgentIo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/api")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    
    @GetMapping("/v1/sessions")
    List<Session> getSessions(){
        return ''
    }

    @GetMapping("/v1/sessions/{sessionId}")
    Session getSession(){
        return ''
    }

    



    

}
