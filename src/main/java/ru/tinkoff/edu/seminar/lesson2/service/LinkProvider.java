package ru.tinkoff.edu.seminar.lesson2.service;

import ru.tinkoff.edu.seminar.lesson2.domain.AbstractLink;

public interface LinkProvider {

    AbstractLink get(String path);
}
