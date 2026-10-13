package com.hojui.AgentIo.dto;


/**
 * PermissionChangeRequest
 */
public class PermissionChangeRequest {

    private PermissionModeType permissionMode;

    public PermissionChangeRequest(PermissionModeType permissionMode) {
        this.permissionMode = permissionMode;
    }

    public PermissionModeType getPermissionMode() {
        return permissionMode;
    }

    public void setPermissionMode(PermissionModeType permissionMode) {
        this.permissionMode = permissionMode;
    }
    


    // public PermissionChangeRequest() {
    //     System.out.println("PermissionChangeRequest");
    // }

    // public PermissionModeType  
    
    
}

