package ru.tinkoff.edu.seminar.lesson2.domain;

import javax.validation.constraints.NotNull;

public abstract class AbstractLink {

    public abstract String getFullUrl();
    public abstract String getShortUrl();

    @NotNull
    private String fullUrl;

    @NotNull
    private String shortUrl;
}
