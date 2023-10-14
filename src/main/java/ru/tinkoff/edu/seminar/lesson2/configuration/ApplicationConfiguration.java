package ru.tinkoff.edu.seminar.lesson2.configuration;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.seminar.lesson2.service.LinkGenerator;

import java.util.Random;

@Configuration
@EnableConfigurationProperties(LengthConfigurationProperty.class)
public class ApplicationConfiguration {

    @Autowired
    private LengthConfigurationProperty length;

    @Bean
    public LinkGenerator linkGenerator(){
        return new LinkGenerator(new Random(), length.getAlphabet(), length.getLink());
    }
}
