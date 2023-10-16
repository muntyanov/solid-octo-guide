package ru.tinkoff.edu.seminar.lesson2.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import ru.tinkoff.edu.seminar.lesson2.service.ShortLinkService;
import ru.tinkoff.edu.seminar.lesson2.web.dto.*;

import java.util.*;
import java.util.stream.Collectors;

import static ru.tinkoff.edu.seminar.lesson2.web.dto.ResponseDto.*;

@RestController
@RequiredArgsConstructor
public class ShortLinksController {

    private final ShortLinkService shortLinkService;

    private final LinkMapper mapper = LinkMapper.INSTANCE;

    @Operation(summary = "Получить короткую ссылку из длинной",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Возвращает объект созданной ссылки"),
                    @ApiResponse(responseCode = "400", description = "Пустая строка не может быть урлом"),
                    @ApiResponse(responseCode = "500", description = "Не удалось сгенерировать")
            })
    @PostMapping("/link")
    public LinkCreateResponseDto shortLinkCreate(@RequestBody @NotEmpty String fullPath) {
        try {
            return mapper.linkToLinkCreateDto(shortLinkService.create(fullPath));
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Operation(summary = "Получить ссылку из короткой",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Возвращает объект созданной ссылки"),
                    @ApiResponse(responseCode = "400", description = "Пустая строка не может быть короткой ссылкой"),
                    @ApiResponse(responseCode = "404", description = "Ссылка не найдена")
            })
    @GetMapping("/link/{shortLink}")
    public LinkListResponseDto shortLinkFind(@PathVariable @NotEmpty String shortLink) {
        var link = shortLinkService.find(shortLink);
        if (link == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        return mapper.linkToListLinkListDto(link);
    }

    @Operation(summary = "Удалить короткую ссылку",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ссылка удалена"),
                    @ApiResponse(responseCode = "404", description = "Ссылка не найдена")
            })
    @DeleteMapping("/link/{shortLink}")
    public ResponseEntity shortLinkDelete(@PathVariable @NotEmpty String shortLink) {
        if (shortLinkService.delete(shortLink))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Получить короткую ссылку из списка длинных, запоминая вероятность открытия каждой",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Возвращает объект созданной ссылки"),
                    @ApiResponse(responseCode = "400", description = "Введены не корректные данные"),
                    @ApiResponse(responseCode = "500", description = "Не удалось сгенерировать")
            })
    @PostMapping("/link/probability")
    public ProbabilityLinkCreateResponseDto shortLinkCreate(
            @RequestBody @Valid ProbabilityLinkCreateRequestDto dto
    ) {
        try {
            return mapper.linkToLinkListCreateDto(shortLinkService.create(dto.getProbability()));
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Operation(summary = "Возвращает список коротких ссылок",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Возвращает список созданных ссылок"),
                    @ApiResponse(responseCode = "404", description = "Ссылок ещё нет"),
            })
    @GetMapping("/link")
    public Collection<LinkListResponseDto> shortLinks() {
        var links = shortLinkService.findAll();
        if (links.isEmpty())
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        return shortLinkService.findAll().stream()
                .map(l -> mapper.linkToListLinkListDto(l))
                .collect(Collectors.<LinkListResponseDto>toList());
    }
}
