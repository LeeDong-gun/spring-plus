package org.example.expert.domain.log.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "manager_id")
    private Long managerId;

    @Column(name = "user_id")
    private Long userId;

    public Log(String message, Long managerId, Long userId, Long todoId) {
        this.message = message;
        this.managerId = managerId;
        this.userId = userId;
        this.todoId = todoId;
    }

    @Column(name = "todo_id")
    private Long todoId;
}
