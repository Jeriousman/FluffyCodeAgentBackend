package com.hojui.AgentIo.dto;

public class LlmChangeRequest {

    private String modelName;

    public LlmChangeRequest() {
    }

    public LlmChangeRequest(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public String toString() {
        return "LlmChangeRequest [modelName=" + modelName + "]";
    }

}
