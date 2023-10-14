package ru.tinkoff.edu.seminar.lesson2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.tinkoff.edu.seminar.lesson2.domain.AbstractLink;
import ru.tinkoff.edu.seminar.lesson2.domain.Link;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

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

    @Test
    void should_concurrently_create() throws InterruptedException {
        var fixedThreadPoolExecutor = Executors.newFixedThreadPool(100);
        var cdl = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            fixedThreadPoolExecutor.execute(() -> {
                assertDoesNotThrow(() -> service.create("testString" + finalI));
                cdl.countDown();
            });
        }
        cdl.await();
        fixedThreadPoolExecutor.shutdownNow();
        assertEquals(1000, holder.allShortLinks().size());
    }

    @Test
    void should_concurrently_create_and_find() throws InterruptedException {
        var listShortLinks = new ArrayList<String>();
        for (int i = 0; i < 1000; i++) {
            listShortLinks.add(service.create("testString" + i).getShortUrl());
        }
        var fixedThreadPoolExecutor = Executors.newFixedThreadPool(100);
        var cdl = new CountDownLatch(1000);
        for (String link : listShortLinks) {
            fixedThreadPoolExecutor.execute(() -> {
                assertDoesNotThrow(() -> service.find(link));
                cdl.countDown();
            });
        }
        cdl.await();
        fixedThreadPoolExecutor.shutdownNow();
        assertEquals(1000, holder.allShortLinks().size());
    }

    static String shortLink = "short";
    static String fullLink = "fullLink";
}