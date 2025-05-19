package com.example.cookingbackend.Repository;

import com.example.cookingbackend.Model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByName(String name);

    Optional<Ingredient> findByNameAndUnit(String name, String unit);

    List<Ingredient> findByNameIn(Collection<String> names);

}
