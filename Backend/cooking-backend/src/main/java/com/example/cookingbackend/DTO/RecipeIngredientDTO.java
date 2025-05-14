package com.example.cookingbackend.DTO;

public class RecipeIngredientDTO {
    public IngredientDTO ingredient;
    public String quantity;

    public RecipeIngredientDTO() {}
    public RecipeIngredientDTO(String quantity, IngredientDTO ingredient) {
        this.quantity = quantity;
        this.ingredient = ingredient;
    }
}