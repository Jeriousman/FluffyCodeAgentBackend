package com.hojui.AgentIo.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
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



// @dataclass
// class MessageItem:
//     role: str                                  # 'user' | 'assistant' | 'tool' (+ 'system')
//     content: str | None                        # 본문 (assistant가 tool만 부르면 None일 수 있음)
//     token_count: int | None = None
//     tool_calls: list[dict[str, Any]] | None = None   # assistant가 tool을 부를 때만
//     tool_call_id: str | None = None                  # tool 결과 메시지일 때만


@Entity 
@Data 
@Table(name = "message")
public class MessageEntity {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long messageId;

    // @GeneratedValue //(strategy = GenerationType.IDENTITY) 
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7) // v7은 앞부분이 시간이라 생성 순서대로 정렬돼요. 숫자 id처럼 인덱스 끝에 붙어서 빠르고, 추측도 어려워요.
    private UUID publicId;

    // 메시지 여러 개가 세션 하나에 속함 (fluffy의 session JSON 파일 1개 = SessionEntity 1개)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private SessionEntity session;

    // fluffy MessageItem.role  ('user' | 'assistant' | 'tool' | 'system')
    @Column(nullable = false)
    private MessageRole messageRole;

    @Column(nullable = true)
    private String content;

    @Column(nullable = true)
    private Integer tokenCount;

    @Column(nullable = true)
    private List<Map<String, Object>> toolCalls;

    @Column(nullable = true)
    private String toolCallId;

    // 메시지가 저장된 시각 (히스토리 화면에 표시용)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    




}
