package com.hojui.AgentIo.dto;
import jakarta.validation.constraints.NotNull;   // ← 추가
import com.hojui.AgentIo.model.EffortMode;

/**
 * EffortChangeRequest
 */
public class EffortChangeRequest {
    @NotNull(message = "effortMode는 필수입니다")
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
