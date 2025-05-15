package com.example.cookingbackend.Service;

import com.example.cookingbackend.Model.RecipeIngredient;
import com.example.cookingbackend.Model.RecipeIngredientKey;
import com.example.cookingbackend.Repository.RecipeIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeIngredientService {
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    public List<RecipeIngredient> getAllRecipeIngredients() {
        return recipeIngredientRepository.findAll();
    }

    public RecipeIngredient createOrUpdate(RecipeIngredient recipeIngredient) {
        return recipeIngredientRepository.save(recipeIngredient);
    }

    public void delete(RecipeIngredientKey key) {
        recipeIngredientRepository.deleteById(key);
    }
}
