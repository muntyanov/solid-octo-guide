package ru.tinkoff.edu.seminar.lesson2.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.seminar.lesson2.domain.Link;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

// наследует 2 интерфейса по четвертому принципу SOLID чтобы мы могли читать из одной базы а писать в другую.
// Например для реализации кэширующей прослойки или https://ru.wikipedia.org/wiki/CQRS
@Component
@Primary // говорим если было 2 бина с таким типом то этот бин будет доминировать
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
