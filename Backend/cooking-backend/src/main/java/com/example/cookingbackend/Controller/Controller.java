package com.example.cookingbackend.Controller;

import com.example.cookingbackend.Model.Recipe;
import com.example.cookingbackend.Service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {

    Service service;
    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getRecipes() {
        return ResponseEntity.ok(service.getRecipes());
    }

    @PostMapping
    public ResponseEntity<List<Recipe>> addRecipe(@RequestBody Recipe recipe) {
        service.addRecipe(recipe);
        return ResponseEntity.ok(service.getRecipes());
    }




    @PatchMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@RequestBody Recipe recipe) {
        try {
            service.updateRecipe(recipe);
            return ResponseEntity.ok(service.getRecipes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update recipe: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable int id) {
        long id_bun= id;
        service.deleteRecipe(id_bun);
        return ResponseEntity.ok(service.getRecipes());
    }

    @GetMapping("/sortByTime")
    public ResponseEntity<List<Recipe>> sortByTime() {
        return ResponseEntity.ok(service.sortByTime());
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getByCategory(@RequestParam String category) {
        return ResponseEntity.ok(service.getByCategory(category));
    }



}
