package com.hojui.AgentIo.model;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data 
@Entity
@Table(name = "users") //To match DB table name, users
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing ID like in SQL
    @Column(unique = true, nullable = false) // it always has to be unique and not null
    private Integer userId;
    @Column(unique = true, nullable = false) // it always has to be unique and not null
    private String email;
    @Column(name = "created_at", nullable = false) // it always has to be not null and db table column name is created_at
    private LocalDateTime createdAt;

}
