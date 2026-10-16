package com.hojui.AgentIo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hojui.AgentIo.model.SessionEntity;
import com.hojui.AgentIo.model.UserEntity;


public interface SessionRepository extends JpaRepository<SessionEntity, Long> {  //  "이 Repository가 어떤 Entity를 다루고, 그 Entity의 id가 어떤 타입인지" 알려 주는 칸입니다.

    @Query("select s from SessionEntity s where s.publicId = :publicId and s.user.username = :username")
    Optional<SessionEntity> findByPublicIdAndUser_Username(String username, UUID publicId);

}
