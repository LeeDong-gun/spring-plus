package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.request.TodoSearchRequestDto;
import org.example.expert.domain.todo.dto.response.TodoSearchResponseDto;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomTodoRepository {

    Page<TodoSearchResponseDto> searchTodos(TodoSearchRequestDto dto, Pageable pageable);

    Optional<Todo> findByIdWithUser(Long todoId);
}
