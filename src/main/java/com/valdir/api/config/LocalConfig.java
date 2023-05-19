package com.valdir.api.config;

import com.valdir.api.domain.User;
import com.valdir.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;


@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @Bean
    public void startDB(){
        User u1 = new User(1, "Gustavo", "gus@gmail.com", "123");
        User u2 = new User(2, "Juliana", "july@gmail.com", "123");

        repository.saveAll(List.of(u1, u2));

    }
}
