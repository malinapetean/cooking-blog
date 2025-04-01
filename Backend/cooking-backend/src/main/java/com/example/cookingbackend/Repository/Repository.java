package com.example.cookingbackend.Repository;

import com.example.cookingbackend.Model.Recipe;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


@org.springframework.stereotype.Repository
public class Repository {

    List<Recipe> recipes = new ArrayList<>();
    public Repository() {

        recipes.add(new Recipe(1, "Creamy Ravioli", "Pasta", "Delicious ravioli with creamy sauce.", 50, "https://i.pinimg.com/736x/06/0f/e5/060fe527e9aad9f7148b2378685d0e2a.jpg"));
        recipes.add(new Recipe(2, "Marry Me Chicken", "Meat", "Juicy chicken with flavorful sauce.", 95, "http://i.pinimg.com/736x/cb/3f/51/cb3f512304bd0308d8f286c1a6be18d9.jpg"));
        recipes.add(new Recipe(3, "Lasagna", "Pasta", "Classic Italian lasagna.", 150, "https://i.pinimg.com/736x/8f/0d/d4/8f0dd4d9f5381460cc6ae99c97960b23.jpg"));
        recipes.add(new Recipe(4, "Greek Salad", "Salads", "Healthy and delicious Greek salad.", 20, "https://i.pinimg.com/736x/d1/e2/a9/d1e2a9c45ae8746c20b326c49414f663.jpg"));
        recipes.add(new Recipe(5, "Tiramisu", "Deserts", "Classic Italian dessert.", 90, "https://i.pinimg.com/736x/48/5f/5e/485f5e8e40ce4996a24d2f5ea41ba367.jpg"));
        recipes.add(new Recipe(6, "Chicken Alfredo", "Pasta", "Creamy chicken alfredo pasta.", 60, "https://i.pinimg.com/736x/f9/bb/a8/f9bba8fa2d1798b9d5d69afa7c163771.jpg"));
        recipes.add(new Recipe(7, "Caesar Salad", "Salads", "Classic Caesar salad.", 30, "https://i.pinimg.com/736x/b2/bc/23/b2bc2347c177cd1d21b13b003c2d5ae9.jpg"));
        recipes.add(new Recipe(8, "Chocolate Cake", "Deserts", "Delicious chocolate cake.", 150, "https://i.pinimg.com/736x/20/7d/26/207d2626197bc2edbc40d5165f726758.jpg"));
        recipes.add(new Recipe(9, "Fritto Misto", "Meat", "Delicious fried seafood.", 120, "https://i.pinimg.com/736x/96/f7/93/96f7930dd034f17dd25759532038e549.jpg"));
        recipes.add(new Recipe(10, "Steak", "Meat", "Juicy steak with flavorful sauce.", 90, "https://i.pinimg.com/736x/e0/24/b2/e024b26a7f553ad39331ed8414a3d910.jpg"));

    }
    public List<Recipe> getRecipes() {
        return recipes;
    }
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }
    public void deleteRecipe(long id) {
        Recipe recipe= recipes.stream()
                        .filter(r-> r.getId()==id)
                                .findFirst()
                                        .orElse(null);
        recipes.remove(recipe);
    }

    public void updateRecipe(Recipe newrecipe)
    {
        long id=newrecipe.getId();
        Recipe newRec = recipes.stream()
                .filter(r -> r.getId()==id)
                .findFirst()
                .orElse(null);
        if(newRec!=null)
        {
            newRec.setTitle(newrecipe.getTitle());
            newRec.setDescription(newrecipe.getDescription());
            newRec.setCategory(newrecipe.getCategory());
            newRec.setTotalTime(newrecipe.getTotalTime());
            newRec.setImage(newrecipe.getImage());

        }
    }

    public List<Recipe> sortByTime(){
        List<Recipe> recipe = new ArrayList<>(getRecipes()); // ✅ Create a new list
        recipe.sort(Comparator.comparing(Recipe::getTotalTime));
        return recipe;
    }

    public List<Recipe> getByCategory(String category) {
        if(category.equals("All"))
            return recipes;
        return recipes.stream()
            .filter(r-> Objects.equals(r.getCategory(), category))
            .toList();


    }





}
