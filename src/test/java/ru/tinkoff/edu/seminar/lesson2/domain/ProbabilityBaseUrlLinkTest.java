package ru.tinkoff.edu.seminar.lesson2.domain;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProbabilityBaseUrlLinkTest {

    @Test
    void should_return_one_url_if_contains_one_url() {
        var link = new ProbabilityBaseUrlLink(Map.of("url", 1), "");
        assertEquals("url", link.getFullUrl());
        assertEquals(1, link.getFullUrlCollection().size());
        assertTrue(link.getShortUrl().isBlank());
    }

    @Test
    void should_return_two_url_if_contains_two_url() {
        var link = new ProbabilityBaseUrlLink(Map.of("url", 1,"url2", 1), "");
        Set<String> setOrResult = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            setOrResult.add(link.getFullUrl());
        }
        assertEquals(2, setOrResult.size());
    }

    @Test
    void should_return_one_url_if_contains_two_url_and_one_with_zero_probability() {
        var link = new ProbabilityBaseUrlLink(Map.of("url1", 0,"url2", 1, "url3", 0), "");
        Set<String> setOrResult = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            setOrResult.add(link.getFullUrl());
        }
        assertEquals(1, setOrResult.size());
        assertEquals("url2", setOrResult.stream().findFirst().get());
    }
}