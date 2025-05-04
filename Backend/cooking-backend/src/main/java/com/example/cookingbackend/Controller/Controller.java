

package com.example.cookingbackend.Controller;

import com.example.cookingbackend.Model.Recipe;
import com.example.cookingbackend.Model.Ingredient;
import com.example.cookingbackend.Service.RecipeService;
import com.example.cookingbackend.Service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class Controller {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public Controller(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @PostMapping
    public ResponseEntity<List<Recipe>> addRecipe(@RequestBody Recipe recipe) {
        recipeService.createRecipe(recipe);
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@RequestBody Recipe recipe) {
        try {
            recipeService.updateRecipe(recipe.getId(), recipe);
            return ResponseEntity.ok(recipeService.getAllRecipes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update recipe: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable int id) {
        long id1 = id;
        recipeService.deleteRecipe(id1);
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/sortByTime")
    public ResponseEntity<List<Recipe>> sortByTime() {
        return ResponseEntity.ok(recipeService.sortByTime());
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getByCategory(@RequestParam String category) {
        return ResponseEntity.ok(recipeService.getByCategory(category));
    }

    @GetMapping("/server")
    public ResponseEntity<Boolean> getServer() {
        return ResponseEntity.ok(true);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Path staticUploadPath = Paths.get("src/main/resources/static/uploads/" + file.getOriginalFilename());
            Files.createDirectories(staticUploadPath.getParent());
            Files.write(staticUploadPath, file.getBytes());

            String fileUrl = "http://localhost:8080/uploads/" + file.getOriginalFilename();
            return ResponseEntity.ok().body(Map.of("url", fileUrl));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/ping")
    public ResponseEntity<Void> getPing() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Ingredient Endpoints
    @GetMapping("/ingredients")
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @PostMapping("/ingredients")
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(ingredientService.createIngredient(ingredient));
    }

    @PatchMapping("/ingredients/{id}")
    public ResponseEntity<?> updateIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        try {
            return ResponseEntity.ok(ingredientService.updateIngredient(id, ingredient));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update ingredient: " + e.getMessage());
        }
    }

    @DeleteMapping("/ingredients/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

