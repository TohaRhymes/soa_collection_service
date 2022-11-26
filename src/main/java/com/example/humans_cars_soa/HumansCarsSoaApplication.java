package com.example.humans_cars_soa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HumansCarsSoaApplication {
    public static void main(String[] args) {
        SpringApplication.run(HumansCarsSoaApplication.class, args);
    }

}
