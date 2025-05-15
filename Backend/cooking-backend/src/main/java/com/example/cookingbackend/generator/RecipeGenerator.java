package com.example.cookingbackend.generator;

import com.example.cookingbackend.Model.Recipe;
import com.example.cookingbackend.websocket.RecipeSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@EnableScheduling
public class RecipeGenerator {

    private final RecipeSocketHandler handler;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RecipeGenerator(RecipeSocketHandler handler) {
        this.handler = handler;
    }

    @Scheduled(fixedRate = 5000)
    public void generateRecipe() {
        Recipe recipe = new Recipe(
                UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE,
                "Random Recipe",
                "Deserts",
                "Delicious stuff",
                (int) (Math.random() * 60),
                "https://picsum.photos/200"
        );

        try {
            String json = objectMapper.writeValueAsString(recipe);
            handler.broadcastRecipe(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
