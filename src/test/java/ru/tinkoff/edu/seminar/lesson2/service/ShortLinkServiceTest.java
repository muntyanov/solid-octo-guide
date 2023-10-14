package ru.tinkoff.edu.seminar.lesson2.service;

import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.tinkoff.edu.seminar.lesson2.configuration.ApplicationConfiguration;
import ru.tinkoff.edu.seminar.lesson2.domain.AbstractLink;
import ru.tinkoff.edu.seminar.lesson2.domain.Link;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(
        webEnvironment = NONE
)
class ShortLinkServiceTest {

    @Autowired
    private ShortLinkService service;

    @Autowired
    private ShortLinkHolder holder;

    @SpyBean
    private LinkGenerator generator;

    @BeforeEach
    public void beforeEach() {
        holder.clear();
    }

    @Test
    public void should_create_only_one_link_by_short_link() {
        when(generator.get()).thenReturn(shortLink);
        service.create(fullLink);
        assertTrue(holder.exists(shortLink));
    }

    @Test
    public void should_create_only_one_link_by_probability() {
        when(generator.get()).thenReturn(shortLink);
        service.create(Map.of(fullLink, 1));
        assertTrue(holder.exists(shortLink));
    }

    @Test
    public void should_throw_exception_if_short_link_exist() {
        AbstractLink link = new Link(fullLink, generator.get());
        holder.save(link);
        when(generator.get()).thenReturn(link.getShortUrl());
        assertThrows(RuntimeException.class, () -> service.create(fullLink));
    }

    @Test
    private void should_find_link() {
        AbstractLink link = service.create(fullLink);
        assertEquals(link, service.find(link.getShortUrl()));
    }

    @Test
    private void should_all_find_link() {
        AbstractLink link = service.create(fullLink);
        assertEquals(1, service.findAll().size());
        assertEquals(link, service.findAll().stream().findFirst().get());
    }

    static String shortLink = "short";
    static String fullLink = "fullLink";
}