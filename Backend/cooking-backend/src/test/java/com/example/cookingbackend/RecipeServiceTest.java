package com.example.cookingbackend;

import com.example.cookingbackend.Model.Recipe;
import com.example.cookingbackend.Repository.Repository;
import com.example.cookingbackend.Service.Service;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RecipeServiceTest {

    @Mock // Mocks the repository
    private Repository recipeRepository;

    @InjectMocks // Injects the mock into service
    private Service recipeService;

    @Test
    void testGetRecipes() {
        // Arrange (Set up mock behavior)
        List<Recipe> mockRecipes = List.of(new Recipe(2,"papa","Pasta","cele mai bune",33,"img.jpg"));
        when(recipeRepository.getRecipes()).thenReturn(mockRecipes);

        // Act (Call the method under test)
        List<Recipe> recipes = recipeService.getRecipes();

        // Assert (Verify expected outcome)
        assertEquals(1, recipes.size());
        assertEquals("papa", recipes.get(0).getTitle());
    }
    @Test
    void testAddRecipe() {
        Recipe recipe = new Recipe();
        recipe.setTitle("papa");
        recipe.setDescription("cele mai bune");
        recipe.setCategory("Pasta");
        recipe.setImage("img.jpg");
        recipe.setTotalTime(110);


        // When: We call addRecipe()
        recipeRepository.addRecipe(recipe);
        List<Recipe> recipes = recipeService.getRecipes();


        // Then: Verify that the recipe is saved and returned correctly
        assertEquals(recipe.getTitle(), recipes.get(0).getTitle());
        assertEquals(recipe.getTotalTime(), recipes.get(0).getTotalTime());

        // Ensure save() was called once
        verify(recipeRepository, times(1)).addRecipe(recipe);




    }

    @Test
    void testFilterSoup(){
        List<Recipe> mockRecipe=List.of(new Recipe(2,"papa","Soup","cea mai buna",33,"img.jpg"));
        when(recipeRepository.getRecipes()).thenReturn(mockRecipe);
        when(recipeRepository.getByCategory("Soup")).thenReturn(mockRecipe);


    }


}
