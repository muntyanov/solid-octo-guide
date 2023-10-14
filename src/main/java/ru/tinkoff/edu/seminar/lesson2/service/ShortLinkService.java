package ru.tinkoff.edu.seminar.lesson2.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.seminar.lesson2.domain.AbstractLink;
import ru.tinkoff.edu.seminar.lesson2.domain.Link;
import ru.tinkoff.edu.seminar.lesson2.domain.ProbabilityBaseUrlLink;

import java.util.Collection;
import java.util.Map;

@Service
public class ShortLinkService {

    @Autowired
    public ShortLinkService(
            LinkGenerator generator,
            ShortLinkHolder holder,
            LinkProvider provider
    ) {
        this.generator = generator;
        this.holder = holder;
        this.provider = provider;
    }

    private final LinkGenerator generator;

    private final ShortLinkHolder holder;

    private final LinkProvider provider;

    public AbstractLink create(String fullPath) {
        AbstractLink link = new Link(fullPath, generator.get());
        return save(link);
    }

    public ProbabilityBaseUrlLink create(Map<String, Integer> fullPath) {
        ProbabilityBaseUrlLink link = new ProbabilityBaseUrlLink(fullPath, generator.get());
        save(link);
        return link;
    }

    private AbstractLink save(AbstractLink link){
        if(holder.exists(link.getShortUrl()))
            throw new RuntimeException("Повторите попытку");
        return holder.save(link);
    }

    public AbstractLink find(String shortPath) {
        return provider.get(shortPath);
    }

    public Collection<AbstractLink> findAll() {
        return holder.allShortLinks();
    }

    public boolean delete(String shortLink) {
        return holder.delete(shortLink);
    }
}
