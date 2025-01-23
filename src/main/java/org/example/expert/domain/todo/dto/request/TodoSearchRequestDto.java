package org.example.expert.domain.todo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TodoSearchRequestDto {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String managerNickname;

}
