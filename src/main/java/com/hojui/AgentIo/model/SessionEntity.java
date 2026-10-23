package com.hojui.AgentIo.model;


import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    // @GeneratedValue //(strategy = GenerationType.IDENTITY) 
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7) // v7은 앞부분이 시간이라 생성 순서대로 정렬돼요. 숫자 id처럼 인덱스 끝에 붙어서 빠르고, 추측도 어려워요.
    private UUID publicId;

    @ManyToOne(fetch = FetchType.LAZY) //세션 여러 개가 유저 한 명에게 속한다는 뜻
    @JoinColumn(name= "user_id", nullable = false) //user_id라는 컬럼명으로 사용되게 설정.
    private UserEntity user;

    private String sessionTitle;

    @CreationTimestamp //- save()로 처음 INSERT될 때 Hibernate가 현재 시각을 넣어 줌.
    @Column(nullable=false, updatable=false)
    private LocalDateTime createdAt;
    @UpdateTimestamp  // 바뀔 때마다 시각을 자동 갱신
    @Column(updatable=true)
    private LocalDateTime updatedAt;

    private int turnCount;

    private long promptTokens;

    private long completionTokens;

    private long totalTokens;
    
    private long cachedTokens;


}
