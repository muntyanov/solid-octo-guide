package ru.tinkoff.edu.seminar.lesson2.service;

import ru.tinkoff.edu.seminar.lesson2.domain.Link;

import java.util.Collection;

public interface ShortLinkHolder {

    Link save(Link link);

    Collection<Link> allShortLinks();

    void clear();

    boolean exists(String shortUrl);
}
