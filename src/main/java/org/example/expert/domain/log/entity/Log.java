package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@RequiredArgsConstructor
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
