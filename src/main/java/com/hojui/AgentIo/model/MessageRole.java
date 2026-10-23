package com.hojui.AgentIo.model;
// Jackson은 Spring이 쓰는 Java 객체 ↔ JSON 변환기
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageRole {
    USER,
    ASSISTANT,
    SYSTEM,
    TOOL;

    // Json으로 변환해서 내보낼 때 (Java → JSON): USER → "user"
    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }

    // Json으로 변환하여 받을 때 (JSON → Java): "user", "USER", "User" → USER
    @JsonCreator
    public static MessageRole fromJson(String value) {
        if (value == null) {
            return null;
        }
        return MessageRole.valueOf(value.trim().toUpperCase()); // trime = 앞뒤공백제거, valueOf = 인자로 들어온 상수로 매치되는 ENUM과 매치
    }
}
