package com.example.cookingbackend.Repository;

import com.example.cookingbackend.Model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
