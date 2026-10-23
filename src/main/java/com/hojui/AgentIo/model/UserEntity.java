package com.hojui.AgentIo.model;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data 
@Entity
@Table(name = "users") //To match DB table name, users
public class UserEntity {
    public UserEntity() {}

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing ID like in SQL
    @Column(unique = true, nullable = false) // it always has to be unique and not null
    private Integer userId;
    @Column(unique = true, nullable = true) // it always has to be unique and not null
    private String email;
    @CreationTimestamp // Automatically set the timestamp when the entity is created
    @Column(name = "created_at", nullable = false) // it always has to be not null and db table column name is created_at
    private LocalDateTime createdAt;
    @Column(unique = true)
    private String username;
    
    @JsonIgnore              // JSON으로 절대 나가지 않게
    private String password;
    @Enumerated(EnumType.STRING) // Store enum as string in DB.  @Enumerated는 Java의 enum 필드를 DB 컬럼에 어떤 형태로 저장할지 JPA에게 알려주는 어노테이션
    private UserRole role; // User role, e.g., "USER", "ADMIN"

    // only Oauth2 related fields
    private String provider;
    @Column(unique = true, name= "provider_id")
    private String providerId;

    // User choice of llm model
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'OPUS'")          // DDL에 DEFAULT 'OPUS' 생성
    private LlmModel llmModel = LlmModel.OPUS;

    // User choice of llm model efforts
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'MEDIUM'")
    private EffortMode effortMode = EffortMode.MEDIUM;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'MANUAL'")
    private PermissionMode permissionMode = PermissionMode.MANUAL;
    


}
