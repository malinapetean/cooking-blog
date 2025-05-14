package com.example.cookingbackend.DTO;

import java.util.List;

public class RecipeDTO {
    public String title;
    public String category;
    public String description;
    public int totalTime;
    public String image;
    public List<RecipeIngredientDTO> recipeIngredients;

    public RecipeDTO() {}

    public RecipeDTO(String title, String category, String description, int totalTime,
                     String image, List<RecipeIngredientDTO> recipeIngredients) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.totalTime = totalTime;
        this.image = image;
        this.recipeIngredients = recipeIngredients;
    }
}

