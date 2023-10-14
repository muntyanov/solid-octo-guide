package ru.tinkoff.edu.seminar.lesson2.web.dto;

import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.seminar.lesson2.domain.AbstractLink;
import ru.tinkoff.edu.seminar.lesson2.domain.Link;

import static org.junit.jupiter.api.Assertions.*;

class LinkMapperTest {

    @Test
    void should_linkToLinkCreateDto_create_null_from_null() {
        assertNull(LinkMapper.INSTANCE.linkToLinkCreateDto(null));
    }

    @Test
    void should_linkToLinkListCreateDto_create_null_from_null() {
        assertNull(LinkMapper.INSTANCE.linkToLinkListCreateDto(null));
    }

    @Test
    void should_linkToListLinkListCreateDto_create_null_from_null() {
        assertNull(LinkMapper.INSTANCE.linkToListLinkListDto(null));
    }

    @Test
    void should_return_empty_collection_on_link_is_not_default_type() {
        assertTrue(LinkMapper.fullPathOfLink(new AbstractLink() {
            @Override
            public String getFullUrl() {
                return null;
            }
            @Override
            public String getShortUrl() {
                return null;
            }
        }).isEmpty());
    }
}