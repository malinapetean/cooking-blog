package com.example.cookingbackend.DTO;

public class RecipeStatsDTO {
    private Double averageTime;
    private Integer minTime;
    private Integer maxTime;
    private Long totalRecipes;

    // Constructor
    public RecipeStatsDTO(Double averageTime, Integer minTime, Integer maxTime, Long totalRecipes) {
        this.averageTime = averageTime;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.totalRecipes = totalRecipes;
    }

    // Getters (required for JSON serialization)
    public Double getAverageTime() { return averageTime; }
    public Integer getMinTime() { return minTime; }
    public Integer getMaxTime() { return maxTime; }
    public Long getTotalRecipes() { return totalRecipes; }
}
