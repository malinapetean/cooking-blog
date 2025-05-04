package com.example.cookingbackend.Repository;

import com.example.cookingbackend.Model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}

