package com.hojui.AgentIo.dto;

import com.hojui.AgentIo.model.EffortMode;

/**
 * EffortChangeRequest
 */
public class EffortChangeRequest {
    
    private EffortMode effortMode;

    public EffortChangeRequest(EffortMode effortMode) {
        this.effortMode = effortMode;
    }

    public EffortMode getEffortMode() {
        return effortMode;
    }

    public void setEffortMode(EffortMode effortMode) {
        this.effortMode = effortMode;
    }

    @Override
    public String toString() {
        return "EffortChangeRequest [effortMode=" + effortMode + "]";
    }

    
    

}
