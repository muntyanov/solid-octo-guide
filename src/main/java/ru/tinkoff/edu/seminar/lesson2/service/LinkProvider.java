package ru.tinkoff.edu.seminar.lesson2.service;

import ru.tinkoff.edu.seminar.lesson2.domain.Link;

public interface LinkProvider {

    Link get(String path);
}
