package ru.tinkoff.edu.seminar.lesson2.web.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Validated
@Getter
@Setter
public class ProbabilityLinkCreateRequestDto {
    @Size(min = 2, message = "вероятность должна быть заполненна для минимум 2 ссылок")
    private Map<
                @Size(min = 10, max = 255, message = "урл должен быть длиннее 9 символов") String,
                @Min(value = 1, message = "вероятность не должна быть нулевая или отрицательная") Integer
            > probability;
}
