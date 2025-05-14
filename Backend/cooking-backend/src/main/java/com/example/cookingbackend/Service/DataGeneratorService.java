package com.example.cookingbackend.Service;

import com.example.cookingbackend.Model.Ingredient;

import java.util.*;

import com.example.cookingbackend.Model.Recipe;
import com.example.cookingbackend.Model.RecipeIngredient;
import com.example.cookingbackend.Model.RecipeIngredientKey;
import com.example.cookingbackend.Repository.IngredientRepository;
import com.example.cookingbackend.Repository.RecipeIngredientRepository;
import com.example.cookingbackend.Repository.RecipeRepository;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class DataGeneratorService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final Faker faker = new Faker();

    public DataGeneratorService(RecipeRepository recipeRepo, IngredientRepository ingredientRepo, RecipeIngredientRepository recipeIngredientRepo) {
        this.recipeRepository = recipeRepo;
        this.ingredientRepository = ingredientRepo;
        this.recipeIngredientRepository = recipeIngredientRepo;
    }


    @Transactional
    public void generateData(int count) {
        final int BATCH_SIZE = 500;
        Faker faker = new Faker();

        List<Ingredient> ingredients = ingredientRepository.findAll();
        if (ingredients.size() < 2) {
            throw new IllegalStateException("At least 2 ingredients are required in the DB.");
        }

        for (int i = 0; i < count; i++) {
            Recipe recipe = new Recipe();
            recipe.setTitle(faker.food().dish() + " #" + i);
            recipe.setCategory(faker.options().option("Meat", "Soup", "Salad", "Dessert", "Pasta"));
            recipe.setDescription(faker.lorem().sentence());
            recipe.setTotalTime(faker.number().numberBetween(10, 120));
            recipe.setImage("https://via.placeholder.com/300");

            recipe = recipeRepository.save(recipe); // persist recipe, get ID

            // Select 2 unique random ingredients
            Collections.shuffle(ingredients);
            Ingredient ing1 = ingredients.get(0);
            Ingredient ing2 = ingredients.get(1);

            List<RecipeIngredient> recipeIngredients = new ArrayList<>();

            RecipeIngredient ri1 = new RecipeIngredient();
            ri1.setRecipe(recipe);
            ri1.setIngredient(ing1);
            ri1.setQuantity(faker.number().numberBetween(50, 500) + "g");
            ri1.setId(new RecipeIngredientKey(recipe.getId(), ing1.getId()));

            RecipeIngredient ri2 = new RecipeIngredient();
            ri2.setRecipe(recipe);
            ri2.setIngredient(ing2);
            ri2.setQuantity(faker.number().numberBetween(50, 500) + "g");
            ri2.setId(new RecipeIngredientKey(recipe.getId(), ing2.getId()));

            recipeIngredients.add(ri1);
            recipeIngredients.add(ri2);

            recipeIngredientRepository.saveAll(recipeIngredients);

            if ((i + 1) % 100 == 0) {
                System.out.println("✅ Inserted " + (i + 1) + " recipes...");
            }
        }

        System.out.println("✅ Done generating " + count + " recipes.");
    }





}
