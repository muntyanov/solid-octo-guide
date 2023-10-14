package ru.tinkoff.edu.seminar.lesson2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.seminar.lesson2.domain.Link;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShortLinkHolderMapTest {
    private ShortLinkHolderMap holder = new ShortLinkHolderMap();

    Link link1 = new Link("testLink1","1");
    Link link2 = new Link("testLink2","2");
    Link duplicateOfLink1 = new Link("testLink1AnotherUrl","1");

    @BeforeEach
    public void beforeEach(){
        holder.clear();
    }

    @Test
    public void should_contains_empty_holder_if_not_link() {
        assertEquals(0, holder.allShortLinks().size());
    }

    @Test
    public void should_delete_link_if_link_contains() {
        holder.save(link1);
        assertTrue(holder.delete(link1.getShortUrl()));
        assertEquals(0, holder.allShortLinks().size());
    }

    @Test
    public void should_not_delete_link_if_link_contains() {
        holder.save(link1);
        assertEquals(1, holder.allShortLinks().size());
        assertFalse(holder.delete(link2.getShortUrl()));
        assertEquals(1, holder.allShortLinks().size());
    }

    @Test
    public void should_not_return_link_if_not_contains_link() {
        assertEquals(0, holder.allShortLinks().size());
        assertNull(holder.get(""));
    }

    @Test
    public void should_contains_one_link_if_one_link_save() {
        holder.save(link1);
        assertEquals(1, holder.allShortLinks().size());
        assertEquals(link1, holder.get(link1.getShortUrl()));
    }

    @Test
    public void should_contains_one_link_if_save_two_duplicate_links() {
        holder.save(link1);
        holder.save(duplicateOfLink1);
        assertEquals(1, holder.allShortLinks().size());
        assertEquals(link1, holder.get(link1.getShortUrl()));
    }

    @Test
    public void should_contains_two_links_if_save_two_links() {
        holder.save(link1);
        holder.save(link2);
        assertEquals(2, holder.allShortLinks().size());
        assertEquals(link1, holder.get(link1.getShortUrl()));
        assertEquals(link2, holder.get(link2.getShortUrl()));
    }

    @Test
    void should_not_contains_link_if_call_clear() {
        holder.save(link1);
        holder.save(link2);
        assertEquals(2, holder.allShortLinks().size());
        holder.clear();
        assertEquals(0, holder.allShortLinks().size());
    }
}