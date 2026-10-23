package com.hojui.AgentIo.dto;
import com.hojui.AgentIo.model.LlmModel;
import jakarta.validation.constraints.NotNull;   // ← 추가
public class LlmChangeRequest {
    @NotNull(message = "llmModel은 필수입니다")
    private LlmModel llmModel;

    public LlmChangeRequest() {
    }

    public LlmChangeRequest(LlmModel llmModel) {
        this.llmModel = llmModel;
    }

    public LlmModel getLlmModel() {
        return llmModel;
    }

    public void setLlmModel(LlmModel llmModel) {
        this.llmModel = llmModel;
    }

    @Override
    public String toString() {
        return "LlmChangeRequest [llmModel=" + llmModel + "]";
    }

}
