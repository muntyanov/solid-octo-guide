package ru.tinkoff.edu.seminar.lesson2.domain;

import java.time.LocalDateTime;

public class Link {
    public Link(String fullUrl, String shortUrl) {
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
    }

    private String fullUrl;
    private String shortUrl;

    private LocalDateTime timeOfCreate = LocalDateTime.now();

    public String getFullUrl() {
        return fullUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public LocalDateTime getTimeOfCreate() {
        return timeOfCreate;
    }
}
