package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Broker {
    public static void main(String[] args) {
        try {
            SpringApplication.run(Broker.class, args);
            System.out.print("Broker is running..");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}