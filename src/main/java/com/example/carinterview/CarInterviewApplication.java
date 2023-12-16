package com.example.carinterview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class CarInterviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarInterviewApplication.class, args);
    }

}
