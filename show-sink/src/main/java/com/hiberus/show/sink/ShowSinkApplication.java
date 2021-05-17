package com.hiberus.show.sink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class ShowSinkApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ShowSinkApplication.class, args);
    }
}
