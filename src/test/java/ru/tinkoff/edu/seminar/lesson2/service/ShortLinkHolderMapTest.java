package ru.tinkoff.edu.seminar.lesson2.service;

import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.seminar.lesson2.domain.Link;

import static org.junit.jupiter.api.Assertions.*;

class ShortLinkHolderMapTest {

    /**
     * Плохой пример теста. При сборке не видим что именно сломалось. Так же мы не видим какая логика в холдере
     * хотя класс полностью покрыт
     */
    @Test
    void holder_test() {
        var holder = new ShortLinkHolderMap();
        var link = new Link("","");
        var link2 = new Link("fafs","1");
        assertEquals(0, holder.allShortLinks().size());
        holder.save(link);
        assertEquals(1, holder.allShortLinks().size());
        assertEquals(link, holder.get(""));
        holder.save(new Link("22",""));
        assertEquals(1, holder.allShortLinks().size());
        assertEquals(link, holder.get(""));
        holder.save(link2);
        assertEquals(2, holder.allShortLinks().size());
        holder.clear();
        assertEquals(0, holder.allShortLinks().size());

    }
}