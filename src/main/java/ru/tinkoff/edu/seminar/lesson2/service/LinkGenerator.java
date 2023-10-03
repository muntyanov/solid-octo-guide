package ru.tinkoff.edu.seminar.lesson2.service;

import ru.tinkoff.edu.seminar.lesson2.domain.Link;

import java.util.Random;

public class LinkGenerator implements LinkProvider {


    public LinkGenerator(Random random, int lengthAlphabet, int length){
        this.random = random;
        this.lengthAlphabet = lengthAlphabet;
        this.length = length;
    }


    private final int length;
    private int lengthAlphabet = 62;
    private Random random;
    @Override
    public Link get(String fullPath) {
        var shortLink = generate();
        return new Link(fullPath, shortLink);
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
