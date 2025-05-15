package com.example.cookingbackend.Service;

import com.example.cookingbackend.Model.RecipeIngredient;
import com.example.cookingbackend.Model.RecipeIngredientKey;
import com.example.cookingbackend.Model.Recipe;
import com.example.cookingbackend.Model.Ingredient;
import com.example.cookingbackend.Repository.IngredientRepository;
import com.example.cookingbackend.Repository.RecipeIngredientRepository;
import com.example.cookingbackend.Repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeIngredientService {
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;
    private RecipeRepository recipeRepository;
    private IngredientRepository ingredientRepository;

    public List<RecipeIngredient> getAllRecipeIngredients() {
        return recipeIngredientRepository.findAll();
    }

    public RecipeIngredient createOrUpdate(RecipeIngredient recipeIngredient) {
        return recipeIngredientRepository.save(recipeIngredient);
    }

    public void delete(RecipeIngredientKey key) {
        recipeIngredientRepository.deleteById(key);
    }
    public void addIngredientToRecipe(Long recipeId, Long ingredientId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow();
        RecipeIngredientKey key= new RecipeIngredientKey();
        RecipeIngredient recipeIngredient = new RecipeIngredient(key,recipe, ingredient,ingredient.getUnit());
        recipeIngredientRepository.save(recipeIngredient);
    }

    public void removeIngredientFromRecipe(Long recipeId, Long ingredientId) {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findByRecipeIdAndIngredientId(recipeId, ingredientId).orElseThrow();
        recipeIngredientRepository.delete(recipeIngredient);
    }

    public List<Ingredient> getIngredientsForRecipe(Long recipeId) {
        return recipeIngredientRepository.findByRecipeId(recipeId)
                .stream()
                .map(RecipeIngredient::getIngredient)
                .toList();
    }


}
