package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.example.expert.domain.todo.dto.request.TodoSearchRequestDto;
import org.example.expert.domain.todo.dto.response.TodoSearchResponseDto;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.comment.entity.QComment.comment;

@Repository
public class CustomTodoRepositoryImpl implements CustomTodoRepository {

    private final JPAQueryFactory queryFactory;

    public CustomTodoRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<TodoSearchResponseDto> searchTodos(TodoSearchRequestDto dto, Pageable pageable) {

        BooleanExpression titleSearch = titleSearchCondition(dto.getTitle());
        BooleanExpression dateSearch = dateSearchCondition(dto.getStartDate(), dto.getEndDate());
        BooleanExpression managerSearch = managerSearchCondition(dto.getManagerNickname());


        List<TodoSearchResponseDto> results = queryFactory
                .select(Projections.constructor(TodoSearchResponseDto.class,
                        todo.title,
                        manager.id.countDistinct(),
                        comment.id.count()
                ))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .where(titleSearch, dateSearch, managerSearch)
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 검색 결과에 따른 페이징 개수
        long total = queryFactory
                .select(todo.id.count())
                .from(todo)
                .where(titleSearch, dateSearch, managerSearch)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    // 제목 검색 조건
    private BooleanExpression titleSearchCondition(String title) {
        return title != null ? todo.title.containsIgnoreCase(title) : null;
    }

    // 날짜 시간 검색 조건
    private BooleanExpression dateSearchCondition(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return todo.createdAt.between(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        } else if (startDate != null) {
            return todo.createdAt.goe(startDate.atStartOfDay());
        } else if (endDate != null) {
            return todo.createdAt.loe(startDate.atTime(23, 59, 59));
        }
        return null;
    }

    // 매니저 검색 조건
    private BooleanExpression managerSearchCondition(String managerNickname) {
        return managerNickname != null ? manager.user.nickname.containsIgnoreCase(managerNickname) : null;
    }

    @Override
    public Optional<Todo> findByIdWithUser (Long todoId) {

        Todo findTodo = queryFactory
                .select(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(findTodo);
    }
}