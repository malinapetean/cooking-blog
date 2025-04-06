package com.example.cookingbackend;

import com.example.cookingbackend.Controller.Controller;
import com.example.cookingbackend.Model.Recipe;
import com.example.cookingbackend.Service.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)

public class RecipeControllerTest {


    private MockMvc mockMvc;

    @Mock
    private Service recipeService;
    @InjectMocks
    private Controller recipeController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void testGetAllRecipes() throws Exception {
        // Arrange
        List<Recipe> recipes = List.of(new Recipe(2,"papa","Pasta","cele mai bune",33,"img.jpg"));
        Mockito.when(recipeService.getRecipes()).thenReturn(recipes);

        mockMvc.perform(get("/recipes"))  // ✅ Use get() from MockMvcRequestBuilders
                .andExpect(status().isOk())   // ✅ Expect HTTP 200
                .andExpect((ResultMatcher) jsonPath("$.length()").value(1))
                .andExpect((ResultMatcher) jsonPath("$[0].title").value("Pizza"));
    }
}