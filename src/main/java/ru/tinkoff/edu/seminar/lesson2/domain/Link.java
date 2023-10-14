package ru.tinkoff.edu.seminar.lesson2.domain;

import javax.validation.constraints.NotNull;


public class Link extends AbstractLink{
    public Link(String fullUrl, String shortUrl) {
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
    }

    @NotNull
    private String fullUrl;

    @NotNull
    private String shortUrl;

    @Override
    public String getFullUrl() {
        return fullUrl;
    }

    @Override
    public String getShortUrl() {
        return shortUrl;
    }
}
