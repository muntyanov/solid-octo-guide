package ru.tinkoff.edu.seminar.lesson2.web.dto;

import java.util.Collection;

public interface ResponseDto {
    record LinkCreateResponseDto(
            String fullUrl,
            String shortUrl
    ) implements ResponseDto {}

    record LinkListResponseDto(
            Collection<String> fullUrl,
            String shortUrl
    ) implements ResponseDto {}

    record ProbabilityLinkCreateResponseDto(
            Collection<String> fullUrl,
            String shortUrl
    ) implements ResponseDto {}
}
