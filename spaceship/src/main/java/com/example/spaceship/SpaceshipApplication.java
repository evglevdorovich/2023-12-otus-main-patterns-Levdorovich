package com.example.spaceship;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SpaceshipApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaceshipApplication.class, args);
    }
}
