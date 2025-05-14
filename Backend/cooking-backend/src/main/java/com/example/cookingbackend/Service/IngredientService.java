package com.example.cookingbackend.Service;

import com.example.cookingbackend.Model.Ingredient;
import com.example.cookingbackend.Repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Ingredient updateIngredient(Long id, Ingredient updatedIngredient) {
        return ingredientRepository.findById(id)
                .map(ingredient -> {
                    ingredient.setName(updatedIngredient.getName());
                    ingredient.setUnit(updatedIngredient.getUnit());
                    return ingredientRepository.save(ingredient);
                })
                .orElse(null);
    }

    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
}
