package com.example.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(MessageRepository repository){
        return args -> {
            log.info("Loading message" + repository.save(new Message("Hi")));
            log.info("Loading message" + repository.save(new Message("Hello world!")));
        };
    }
}
