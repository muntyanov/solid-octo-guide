package ru.tinkoff.edu.seminar.lesson2.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.seminar.lesson2.service.LinkGenerator;

import java.util.Random;

@Configuration
public class ConfigurationProject {

    @Value("${length.alphabet}") // смотри значение в папке resources
    private int lengthAlphabet;

    @Value("${length.link}")
    private int linkLength;

    @Bean("generator") // указываем как определить что подставить из контекста если тип имеет два Bean
    public LinkGenerator linkGenerator(){
        return new LinkGenerator(new Random(), lengthAlphabet, linkLength);
    }
}
