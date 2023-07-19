package com.shaohao.mytask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MytaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(MytaskApplication.class, args);
    }

}
