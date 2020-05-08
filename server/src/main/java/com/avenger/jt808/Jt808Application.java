package com.avenger.jt808;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Jt808Application {

    public static void main(String[] args) {
        SpringApplication.run(Jt808Application.class, args);
    }

}
