package com.hojui.AgentIo.dto;

import com.hojui.AgentIo.model.PermissionMode;

/**
 * PermissionChangeRequest
 */
public class PermissionChangeRequest {

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

