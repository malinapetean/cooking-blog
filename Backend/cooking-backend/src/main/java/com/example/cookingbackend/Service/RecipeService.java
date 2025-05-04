package com.example.cookingbackend.Service;

import com.example.cookingbackend.Model.*;
import com.example.cookingbackend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(Long id, Recipe updatedRecipe) {
        return recipeRepository.findById(id)
                .map(recipe -> {
                    recipe.setTitle(updatedRecipe.getTitle());
                    recipe.setCategory(updatedRecipe.getCategory());
                    recipe.setDescription(updatedRecipe.getDescription());
                    recipe.setTotalTime(updatedRecipe.getTotalTime());
                    recipe.setImage(updatedRecipe.getImage());
                    return recipeRepository.save(recipe);
                })
                .orElseGet(() -> {
                    updatedRecipe.setId(id);
                    return recipeRepository.save(updatedRecipe);
                });
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> sortByTime() {
        return recipeRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Recipe::getTotalTime))
                .collect(Collectors.toList());
    }

    public List<Recipe> getByCategory(String category) {
        return recipeRepository.findAll()
                .stream()
                .filter(recipe -> recipe.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
}
