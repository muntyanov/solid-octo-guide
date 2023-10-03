package ru.tinkoff.edu.seminar.lesson2.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.tinkoff.edu.seminar.lesson2.domain.Link;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// Тест на контекст тут возможно избыточен
@SpringBootTest
class ShortLinkServiceTest {

    @Autowired
    private ShortLinkService service;

    @SpyBean
    private LinkGenerator generator;

    @Test
    private void create() {
        assertNotNull(service);
        assertNotNull(service.getGenerator());
        assertNotNull(service.getHolder());
        assertNotNull(service.getProvider());
        assertNotEquals(service.getProvider(), service.getGenerator());
    }


    @Test
    public void should_create_only_one_link_by_short_link() {
        Link link = service.getGenerator().get("");
        link = service.getHolder().save(link);
        assertEquals(link, service.getHolder().save(link));
    }

    @Test
    public void should_throw_exception_if_short_link_exist() {
        Link link = service.getGenerator().get("");
        service.getHolder().save(link);
        when(generator.get("")).thenReturn(link);
        assertThrows(RuntimeException.class,
                () -> {
                    service.create("");
                });
    }

    @Test
    private void find() {
        Link link = service.create("fasfsa");
        assertEquals(link, service.find(link.getShortUrl()));
    }
}