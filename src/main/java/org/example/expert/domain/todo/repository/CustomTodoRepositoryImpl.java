package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.example.expert.domain.user.entity.QUser.user;

@Repository
public class CustomTodoRepositoryImpl extends QuerydslRepositorySupport implements CustomTodoRepository {

    private final JPAQueryFactory queryFactory;

    public CustomTodoRepositoryImpl(EntityManager entityManager) {
        super(Todo.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Optional<Todo> findByIdWithUser (Long todoId) {
        QTodo todo = QTodo.todo;
        return Optional.ofNullable(
                from(todo)
                        .leftJoin(todo.user, user).fetchJoin()
                        .where(todo.id.eq(todoId))
                        .fetchOne()
        );
    }
}