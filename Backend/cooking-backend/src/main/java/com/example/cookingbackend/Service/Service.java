package com.example.cookingbackend.Service;

import com.example.cookingbackend.Model.Recipe;
import com.example.cookingbackend.Repository.Repository;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    Repository repository;
    public Service(Repository repository) {
        this.repository = repository;
    }

    public List<Recipe> getRecipes() {
        return repository.getRecipes();
    }
    public void addRecipe(Recipe recipe) {

        repository.addRecipe(recipe);
    }

    public void deleteRecipe(long id) {
        repository.deleteRecipe(id);
    }

    public void updateRecipe(Recipe newrecipe) throws Exception {
        if(newrecipe.getDescription().isEmpty()|| newrecipe.getImage().isEmpty() || newrecipe.getTitle().isEmpty()) {
            throw new Exception("This fields can't be empty");
        }
        if(newrecipe.getTotalTime()<0)
            throw new Exception("This field can't be negative");

        repository.updateRecipe(newrecipe);
    }

    public List<Recipe> sortByTime()
    {
        return repository.sortByTime();
    }
    public List<Recipe> getByCategory(String category) {
        return repository.getByCategory(category);
    }
}


