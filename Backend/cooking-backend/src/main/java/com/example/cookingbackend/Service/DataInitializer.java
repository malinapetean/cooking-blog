package com.example.cookingbackend.Service;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final DataGeneratorService dataGeneratorService;

    public DataInitializer(DataGeneratorService dataGeneratorService) {
        this.dataGeneratorService = dataGeneratorService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        new Thread(() -> {
            ///System.out.println("🚀 Application started — generating fake data...");
            try {
                dataGeneratorService.generateData(0);
            } catch (Exception e) {
                e.printStackTrace();
                //System.err.println("❌ Failed to generate data: " + e.getMessage());
            }
        }).start(); // ✅ Runs in a background thread
    }
}

