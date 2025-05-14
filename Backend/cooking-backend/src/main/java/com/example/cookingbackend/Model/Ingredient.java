package com.example.cookingbackend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or AUTO
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private String unit;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RecipeIngredient> recipeIngredients;

    public Ingredient(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

}
