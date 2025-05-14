package com.example.cookingbackend.Repository;

import com.example.cookingbackend.Model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByName(String name);

    Optional<Ingredient> findByNameAndUnit(String name, String unit);

    List<Ingredient> findByNameIn(Collection<String> names);

}
