package com.example.spaceship;

import com.example.spaceship.queue.SystemThread;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SpaceshipApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(SpaceshipApplication.class, args);
        context.getBean(SystemThread.class).start();
    }
}
