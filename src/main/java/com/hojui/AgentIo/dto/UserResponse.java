package com.hojui.AgentIo.dto;

import java.time.LocalDateTime;
import com.hojui.AgentIo.model.*;

// 클라이언트에게 보여줄 필드만 고른다. password, providerId는 절대 넣지 않는다.
public record UserResponse(
        Integer userId,
        String username,
        String email,
        UserRole role,
        LlmModel llmModel,
        EffortMode effortMode,
        PermissionMode permissionMode,
        LocalDateTime createdAt
) {
    public static UserResponse from(UserEntity u) {
        return new UserResponse(u.getUserId(), u.getUsername(), u.getEmail(), u.getRole(),
                u.getLlmModel(), u.getEffortMode(), u.getPermissionMode(), u.getCreatedAt());
    }
}