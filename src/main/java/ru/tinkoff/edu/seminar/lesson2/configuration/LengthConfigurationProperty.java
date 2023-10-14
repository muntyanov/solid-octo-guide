package ru.tinkoff.edu.seminar.lesson2.configuration;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@Getter
@Setter
@Validated
@ConfigurationProperties(prefix="length")
public class LengthConfigurationProperty {

    @Min(value = 2, message = "Алфавит должен быть длиннее 1 символа")
    @Max(value = 62, message = "Алфавит должен быть короче 63 символов")
    private int alphabet;

    @Min(value = 2, message = "Ссылка должна быть длиннее 1 символа")
    @Max(value = 6, message = "Ссылка должна быть короче 7 символов")
    private int link;

}
