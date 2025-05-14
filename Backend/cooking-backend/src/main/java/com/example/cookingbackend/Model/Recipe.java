package com.example.cookingbackend.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String title;
    @Column
    private String category;
    @Column
    private String description;
    @Column
    private int totalTime;
    @Column
    private String image;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RecipeIngredient> recipeIngredients;

    public Recipe(long id,String title, String category, String description, int totalTime, String image) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.totalTime = totalTime;
        this.image = image;

    }

    public Recipe() {

    }
}