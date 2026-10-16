package com.hojui.AgentIo.dto;
import com.hojui.AgentIo.model.LlmMode;

public class LlmChangeRequest {

    private LlmMode modelName;

    public LlmChangeRequest() {
    }

    public LlmChangeRequest(LlmMode modelName) {
        this.modelName = modelName;
    }

    public LlmMode getModelName() {
        return modelName;
    }

    public void setModelName(LlmMode modelName) {
        this.modelName = modelName;
    }

    @Override
    public String toString() {
        return "LlmChangeRequest [modelName=" + modelName + "]";
    }

}
