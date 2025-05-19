

package com.example.cookingbackend.Controller;

import com.example.cookingbackend.DTO.RecipeDTO;
import com.example.cookingbackend.DTO.RecipeStatsDTO;
import com.example.cookingbackend.Model.Recipe;
import com.example.cookingbackend.Model.Ingredient;
import com.example.cookingbackend.Model.User;
import com.example.cookingbackend.Repository.MonitoredUserRepository;
import com.example.cookingbackend.Repository.RecipeRepository;
import com.example.cookingbackend.Service.RecipeIngredientService;
import com.example.cookingbackend.Service.RecipeService;
import com.example.cookingbackend.Service.IngredientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org. springframework. data. domain. Pageable;
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
    private final RecipeIngredientService recipeIngredientService;
    private final RecipeRepository recipeRepository;
    private final MonitoredUserRepository monitoredUserRepository;


    public Controller(RecipeService recipeService, IngredientService ingredientService, RecipeIngredientService recipeIngredientService, RecipeRepository recipeRepository, MonitoredUserRepository monitoredUserRepository) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.recipeIngredientService = recipeIngredientService;
        this.recipeRepository = recipeRepository;
        this.monitoredUserRepository = monitoredUserRepository;

    }
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        System.out.println("✅ /health endpoint hit!");

        return ResponseEntity.ok("Backend is alive!");
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/admin/monitored-users")
    public ResponseEntity<?> getMonitoredUsers(User user) {
//        if (user.getIsAdmin() != 1) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized");
//        }
        return ResponseEntity.ok(monitoredUserRepository.findAll());
    }
//    @GetMapping
//    public ResponseEntity<List<Recipe>> getRecipes(@RequestParam(defaultValue = "100") int limit) {
//        Pageable pageable = PageRequest.of(0, limit);
//        Page<Recipe> page = recipeRepository.findLimitedRecipes(pageable);
//        return ResponseEntity.ok(page.getContent());
//    }

    @PostMapping
    public ResponseEntity<?> addRecipe(@RequestBody RecipeDTO recipeDTO) {
        try {
            recipeService.addRecipeFromDto(recipeDTO);
            return ResponseEntity.ok(recipeService.getAllRecipes());
        } catch (Exception e) {
            e.printStackTrace(); // This logs to server
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Failed to add recipe",
                            "details", e.getMessage()
                    ));
        }
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

    // Many-to-Many Endpoints
    @PostMapping("/recipes/{recipeId}/ingredients/{ingredientId}")
    public ResponseEntity<String> addIngredientToRecipe(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
        try {
            recipeIngredientService.addIngredientToRecipe(recipeId, ingredientId);
            return ResponseEntity.ok("Ingredient added to recipe successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/recipes/{recipeId}/ingredients/{ingredientId}")
    public ResponseEntity<String> removeIngredientFromRecipe(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
        try {
            recipeIngredientService.removeIngredientFromRecipe(recipeId, ingredientId);
            return ResponseEntity.ok("Ingredient removed from recipe.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/recipes/{recipeId}/ingredients")
    public ResponseEntity<List<Ingredient>> getIngredientsForRecipe(@PathVariable Long recipeId) {
        try {
            List<Ingredient> ingredients = recipeIngredientService.getIngredientsForRecipe(recipeId);
            return ResponseEntity.ok(ingredients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping("/statistics")
    public ResponseEntity<RecipeStatsDTO> getStats() {
        return ResponseEntity.ok(recipeService.getRecipeStats());
    }

}

