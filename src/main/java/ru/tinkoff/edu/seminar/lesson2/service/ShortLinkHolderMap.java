package ru.tinkoff.edu.seminar.lesson2.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.seminar.lesson2.domain.Link;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Primary
public class ShortLinkHolderMap implements ShortLinkHolder, LinkProvider {

    private ConcurrentHashMap<String, Link> map = new ConcurrentHashMap<>();

    @Override
    public Link get(String shortPath) {
        return map.get(shortPath);
    }

    @Override
    public Link save(Link link) {
        if(map.containsKey(link.getShortUrl()))
            return map.get(link.getShortUrl());
        map.put(link.getShortUrl(), link);
        return link;
    }

    @Override
    public Collection<Link> allShortLinks() {
        return map.values();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean exists(String shortUrl) {
        return map.containsKey(shortUrl);
    }
}
