package com.example.spaceship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SpaceshipApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaceshipApplication.class, args);
    }
}
