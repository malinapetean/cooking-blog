package com.example.cookingbackend;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.cookingbackend.Repository")
@EntityScan("com.example.cookingbackend.Model")
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
