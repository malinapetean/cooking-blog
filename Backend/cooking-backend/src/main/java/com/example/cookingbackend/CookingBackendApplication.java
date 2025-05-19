package com.example.cookingbackend;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CookingBackendApplication {

    public static void main(String[] args) {
        System.out.println("🚀 MAIN start");
        SpringApplication.run(CookingBackendApplication.class, args);
        System.out.println("✅ App started on port 8080");
    }
    @PostConstruct
    public void logDbUrl() {
        System.out.println("🔍 SPRING_DATASOURCE_URL: " + System.getenv("SPRING_DATASOURCE_URL"));
        System.out.println("🔍 SPRING_DATASOURCE_USERNAME: " + System.getenv("SPRING_DATASOURCE_USERNAME"));
        System.out.println("🔍 SPRING_DATASOURCE_PASSWORD: " + System.getenv("SPRING_DATASOURCE_PASSWORD"));
    }

}
