package org.example.expert.domain.log.service;

import org.springframework.transaction.annotation.Transactional;

public interface LogService {

    void saveLog(String message, Long managerId, Long userId, Long todoId);
}
