package com.example.cookingbackend.config;

import com.example.cookingbackend.websocket.RecipeSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final RecipeSocketHandler recipeSocketHandler;

    public WebSocketConfig(RecipeSocketHandler recipeSocketHandler) {
        this.recipeSocketHandler = recipeSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(recipeSocketHandler, "/ws/recipes").setAllowedOrigins("*");
    }
}
