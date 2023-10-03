package ru.tinkoff.edu.seminar.lesson2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.seminar.lesson2.domain.Link;

import java.util.Collection;

@Service
public class ShortLinkService {

    @Autowired
    public ShortLinkService(
            @Qualifier("generator") LinkProvider generator,
            ShortLinkHolder holder,
            LinkProvider provider
    ) {
        this.generator = generator;
        this.holder = holder;
        this.provider = provider;
    }

    private LinkProvider generator;

    private ShortLinkHolder holder;

    private LinkProvider provider;

    public Link create(String fullPath) {
        Link link;
        link = generator.get(fullPath);
        if(holder.exists(link.getShortUrl()))
            throw new RuntimeException("Повторите попытку");
        return holder.save(link);
    }

    public Link find(String shortPath) {
        return provider.get(shortPath);
    }

    public LinkProvider getGenerator() {
        return generator;
    }

    public ShortLinkHolder getHolder() {
        return holder;
    }

    public LinkProvider getProvider() {
        return provider;
    }

    public Collection<Link> findAll() {
        return holder.allShortLinks();
    }
}
