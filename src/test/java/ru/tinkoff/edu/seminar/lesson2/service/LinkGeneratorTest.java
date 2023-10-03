package ru.tinkoff.edu.seminar.lesson2.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkGeneratorTest {

    private Random random = new Random();
    @Test
    public void link_generator_must_return_string_with_length_more_zero(){
        LinkGenerator linkGenerator = new LinkGenerator(random, 10, 1);
        assertTrue(linkGenerator.get("").getShortUrl().length() > 0);
    }

    @Test
    public void link_generator_must_return_one_random_number(){
        LinkGenerator linkGenerator = new LinkGenerator(random, 10, 1);
        assertDoesNotThrow(() -> {
            Integer.parseInt(linkGenerator.get("").getShortUrl());
        });
    }

    @Test
    public void link_generator_must_return_any_random_number(){
        LinkGenerator linkGenerator = new LinkGenerator(random, 10, 6);
        var i = Integer.parseInt(linkGenerator.get("").getShortUrl());
        var i2 = Integer.parseInt(linkGenerator.get("").getShortUrl());
        assertNotEquals(i, i2);
    }

    @Test
    public void link_generator_must_return_url_with_fullPath_argument(){
        LinkGenerator linkGenerator = new LinkGenerator(random, 10, 1);
        assertEquals("fullPath", linkGenerator.get("fullPath").getFullUrl());
    }

    @Test
    public void link_generator_must_return_char_a_on_11(){
        Random rnd = mock(Random.class);
        when(rnd.nextInt(11)).thenReturn(10);
        LinkGenerator linkGenerator = new LinkGenerator(rnd, 11, 1);
        assertEquals("a", linkGenerator.get("").getShortUrl());
    }

    @Test
    public void link_generator_must_return_char_z_on_35(){
        Random rnd = mock(Random.class);
        when(rnd.nextInt(36)).thenReturn(35);
        LinkGenerator linkGenerator = new LinkGenerator(rnd, 36, 1);
        assertEquals("z", linkGenerator.get("").getShortUrl());
    }

    @Test
    public void link_generator_must_return_char_A_on_36(){
        Random rnd = mock(Random.class);
        when(rnd.nextInt(62)).thenReturn(36);
        LinkGenerator linkGenerator = new LinkGenerator(rnd, 62, 1);
        assertEquals("A", linkGenerator.get("").getShortUrl());
    }

    @Test
    public void link_generator_must_return_char_Z_on_61(){
        Random rnd = mock(Random.class);
        when(rnd.nextInt(62)).thenReturn(61);
        LinkGenerator linkGenerator = new LinkGenerator(rnd, 62, 1);
        assertEquals("Z", linkGenerator.get("").getShortUrl());
    }


    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10})
    public void link_generator_must_return_string_with_concrete_length(int i){
        LinkGenerator linkGenerator = new LinkGenerator(random, 62, i);
        assertTrue(linkGenerator.get("").getShortUrl().length() == i);
    }
}