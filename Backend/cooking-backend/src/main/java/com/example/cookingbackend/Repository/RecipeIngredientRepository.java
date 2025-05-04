package com.example.cookingbackend.Repository;

import com.example.cookingbackend.Model.RecipeIngredient;
import com.example.cookingbackend.Model.RecipeIngredientKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, RecipeIngredientKey> {
}