package org.example.expert.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.example.expert.domain.log.service.LogService;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLoggingAspect {

    @Autowired
    private LogService logService;

    @AfterThrowing(pointcut = "execution(* org.example.expert.domain.log.service.*.*(..))", throwing = "ex")
    public void logException (JoinPoint joinPoint, Exception e) {
        Object[] args = joinPoint.getArgs();

        User user = (User) args[0];
        Todo todo = (Todo) args[1];

        logService.saveLog(
                "Manager registration failed: " + e.getMessage(),
                null,
                user.getId(),
                todo.getId()
        );
    }
}
