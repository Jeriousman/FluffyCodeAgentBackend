package com.hojui.AgentIo.dto;
import jakarta.validation.constraints.NotNull;   // ← 추가
import com.hojui.AgentIo.model.PermissionMode;

/**
 * PermissionChangeRequest
 */
public class PermissionChangeRequest {
    @NotNull(message = "permissionMode는 필수입니다")
    private PermissionMode permissionMode;

    public PermissionChangeRequest(PermissionMode permissionMode) {
        this.permissionMode = permissionMode;
    }

    public PermissionMode getPermissionMode() {
        return permissionMode;
    }

    public void setPermissionMode(PermissionMode permissionMode) {
        this.permissionMode = permissionMode;
    }

    @Override
    public String toString() {
        return "PermissionChangeRequest [permissionMode=" + permissionMode + "]";
    }
    



}

