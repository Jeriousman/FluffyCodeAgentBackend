package com.hojui.AgentIo.model;


import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;


@Data 
@Entity 
@Table(name = "sessions") //To match DB table name
public class SessionEntity {

    // @UuidGenerator(style = UuidGenerator.Style.VERSION_7) // v7은 앞부분이 시간이라 생성 순서대로 정렬돼요. 숫자 id처럼 인덱스 끝에 붙어서 빠르고, 추측도 어려워요.
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long sessionId;

    @GeneratedValue //(strategy = GenerationType.IDENTITY) 
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7) // v7은 앞부분이 시간이라 생성 순서대로 정렬돼요. 숫자 id처럼 인덱스 끝에 붙어서 빠르고, 추측도 어려워요.
    private UUID publicId;

    @JoinColumn(name= "user_id", nullable = false) //user_id라는 컬럼명으로 사용되게 설정.
    private UserEntity user;

    private String sessionTitle;

    @Column(nullable=false, updatable=false)
    private LocalDateTime createdAt;
    @Column(updatable=true)
    private LocalDateTime updatedAt;
    

}
