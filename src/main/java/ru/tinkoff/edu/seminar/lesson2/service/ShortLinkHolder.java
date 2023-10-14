package ru.tinkoff.edu.seminar.lesson2.service;

import ru.tinkoff.edu.seminar.lesson2.domain.AbstractLink;
import ru.tinkoff.edu.seminar.lesson2.domain.Link;

import java.util.Collection;

public interface ShortLinkHolder {

    AbstractLink save(AbstractLink link);

    Collection<AbstractLink> allShortLinks();

    void clear();

    boolean exists(String shortUrl);

    boolean delete(String shortLink);
}
