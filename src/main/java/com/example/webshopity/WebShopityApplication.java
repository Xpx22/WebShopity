package com.example.webshopity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class WebShopityApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebShopityApplication.class, args);
    }
}
