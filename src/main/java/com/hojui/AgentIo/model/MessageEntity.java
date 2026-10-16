package com.hojui.AgentIo.model;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Data 
@Table(name = "message")
public class MessageEntity {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long messageId;

    @GeneratedValue //(strategy = GenerationType.IDENTITY) 
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7) // v7은 앞부분이 시간이라 생성 순서대로 정렬돼요. 숫자 id처럼 인덱스 끝에 붙어서 빠르고, 추측도 어려워요.
    private UUID publicId;


}
