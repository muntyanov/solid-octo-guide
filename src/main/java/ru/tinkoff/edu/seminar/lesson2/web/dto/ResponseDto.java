package ru.tinkoff.edu.seminar.lesson2.web.dto;

import java.util.Collection;

public class ResponseDto {
    public record LinkCreateResponseDto(
            String fullUrl,
            String shortUrl
    ) {
    }

    public record LinkListResponseDto(
            Collection<String> fullUrl,
            String shortUrl
    ) {
    }

    public record ProbabilityLinkCreateResponseDto (
            Collection<String> fullUrl,
            String shortUrl
    ) {}
}
