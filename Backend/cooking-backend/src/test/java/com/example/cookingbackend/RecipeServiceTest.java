package com.example.cookingbackend;

import com.example.cookingbackend.Model.Recipe;
import com.example.cookingbackend.Repository.Repository;
import com.example.cookingbackend.Service.Service;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Mockito.when(recipeRepository.getRecipes()).thenReturn(mockRecipes);

        // Act (Call the method under test)
        List<Recipe> recipes = recipeService.getRecipes();

        // Assert (Verify expected outcome)
        assertEquals(1, recipes.size());
        assertEquals("papa", recipes.get(0).getTitle());
    }
}
