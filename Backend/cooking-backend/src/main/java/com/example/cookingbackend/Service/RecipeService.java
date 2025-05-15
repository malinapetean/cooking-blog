package com.example.cookingbackend.Service;

import com.example.cookingbackend.DTO.RecipeDTO;
import com.example.cookingbackend.DTO.RecipeIngredientDTO;
import com.example.cookingbackend.DTO.RecipeStatsDTO;
import com.example.cookingbackend.Model.*;
import com.example.cookingbackend.Repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }
    @Transactional
    public void addRecipeFromDto(RecipeDTO dto) {
        // Step 1: Create and save recipe
        Recipe recipe = new Recipe();
        recipe.setTitle(dto.title);
        recipe.setCategory(dto.category);
        recipe.setDescription(dto.description);
        recipe.setTotalTime(dto.totalTime);
        recipe.setImage(dto.image);

        Recipe savedRecipe = recipeRepository.save(recipe);
        recipeRepository.flush(); // Ensure ID is set

        // Step 2: Prepare ingredients and relationships
        for (RecipeIngredientDTO riDto : dto.recipeIngredients) {
            String name = riDto.ingredient.name.trim();
            String unit = riDto.ingredient.unit.trim();

            // Find or create ingredient
            Ingredient ingredient = ingredientRepository.findByName(name).orElse(null);
            if (ingredient == null) {
                ingredient = new Ingredient(name, unit);
                ingredient = ingredientRepository.save(ingredient);
                ingredientRepository.flush(); // Ensure ID is set
            }

            // Composite key
            RecipeIngredientKey key = new RecipeIngredientKey(savedRecipe.getId(), ingredient.getId());

            // Create relationship
            RecipeIngredient ri = new RecipeIngredient();
            ri.setId(key);
            ri.setRecipe(savedRecipe);
            ri.setIngredient(ingredient);
            ri.setQuantity(riDto.quantity.trim());

            // 💡 Use merge instead of save to avoid NonUniqueObjectException
            entityManager.merge(ri);
        }
    }






    @Transactional
    public Recipe updateRecipe(Long id, Recipe updatedRecipe) {
        return recipeRepository.findById(id)
                .map(existingRecipe -> {
                    // Basic field updates
                    existingRecipe.setTitle(updatedRecipe.getTitle());
                    existingRecipe.setCategory(updatedRecipe.getCategory());
                    existingRecipe.setDescription(updatedRecipe.getDescription());
                    existingRecipe.setTotalTime(updatedRecipe.getTotalTime());
                    existingRecipe.setImage(updatedRecipe.getImage());

                    // Clear old ingredients
                    existingRecipe.getRecipeIngredients().clear();

                    // Add updated ingredients
                    for (RecipeIngredient updatedRI : updatedRecipe.getRecipeIngredients()) {
                        Ingredient updatedIngredient = updatedRI.getIngredient();

                        // Try to find an existing ingredient by name and unit
                        Ingredient existingIngredient = ingredientRepository
                                .findByNameAndUnit(updatedIngredient.getName(), updatedIngredient.getUnit())
                                .orElseGet(() -> {
                                    // If not found, create a new ingredient
                                    Ingredient newIngredient = new Ingredient();
                                    newIngredient.setName(updatedIngredient.getName());
                                    newIngredient.setUnit(updatedIngredient.getUnit());
                                    return ingredientRepository.save(newIngredient);
                                });

                        // Create composite key
                        RecipeIngredientKey key = new RecipeIngredientKey();
                        key.setRecipeId(existingRecipe.getId());
                        key.setIngredientId(existingIngredient.getId());

                        // Create and link new RecipeIngredient
                        RecipeIngredient newRecipeIngredient = new RecipeIngredient();
                        newRecipeIngredient.setId(key);
                        newRecipeIngredient.setRecipe(existingRecipe);
                        newRecipeIngredient.setIngredient(existingIngredient);
                        newRecipeIngredient.setQuantity(updatedRI.getQuantity());

                        existingRecipe.getRecipeIngredients().add(newRecipeIngredient);
                    }

                    return recipeRepository.save(existingRecipe);
                })
                .orElseThrow(() -> new RuntimeException("Recipe not found with ID: " + id));
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

    public RecipeStatsDTO getRecipeStats() {
        Object result = recipeRepository.getRecipeStatsLimited();

        // First, cast the result to Object[]
        Object[] stats = (Object[]) result;

        // Then, cast each individual element safely
        Double averageTime = stats[0] != null ? ((Number) stats[0]).doubleValue() : null;
        Integer minTime = stats[1] != null ? ((Number) stats[1]).intValue() : null;
        Integer maxTime = stats[2] != null ? ((Number) stats[2]).intValue() : null;
        Long totalRecipes = stats[3] != null ? ((Number) stats[3]).longValue() : null;

        return new RecipeStatsDTO(averageTime, minTime, maxTime, totalRecipes);
    }
}
