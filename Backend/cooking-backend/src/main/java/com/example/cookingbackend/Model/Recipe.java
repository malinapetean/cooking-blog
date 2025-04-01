package com.example.cookingbackend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data

//@Table(name = "Recipe")
public class Recipe {
    //@Id
    //@Column(name = "ID")
    private long id;

    //@Column(name = "Title")
    private String title;

    //@Column(name = "Category")
    private String category;

    //@Column(name = "Description")
    private String description;

    //@Column(name = "TotalTime")
    private int totalTime;

    //@Column(name = "Image")
    private String image;


    public Recipe(long id, String title, String category, String description, int totalTime, String image) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.totalTime = totalTime;
        this.image = image;
    }
    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public int getTotalTime() { return totalTime; }
    public String getImage() { return image; }

    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setTotalTime(int totalTime) { this.totalTime = totalTime; }
    public void setImage(String image) { this.image = image; }


    // Default constructor (needed for frameworks like Spring)
    public Recipe() {}


}
