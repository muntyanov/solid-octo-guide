package ru.tinkoff.edu.seminar.lesson2.service;

import ru.tinkoff.edu.seminar.lesson2.domain.AbstractLink;
import ru.tinkoff.edu.seminar.lesson2.domain.Link;

import java.util.List;
import java.util.Random;

public class LinkGenerator {


    public LinkGenerator(Random random, int lengthAlphabet, int length){
        this.random = random;
        this.lengthAlphabet = lengthAlphabet;
        this.length = length;
    }

    private final int length;
    private int lengthAlphabet;
    private Random random;

    public String get() {
        var shortLink = generate();
        return shortLink;
    }

    private String generate() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++) {
            int rnd = random.nextInt(lengthAlphabet);
            if (rnd < 10)
                sb.append(rnd);
            else if (rnd < 36)
                sb.append((char) (rnd - 10 + 'a'));
            else
                sb.append((char) (rnd - 36 + 'A'));
        }
        return sb.toString();
    }
}
