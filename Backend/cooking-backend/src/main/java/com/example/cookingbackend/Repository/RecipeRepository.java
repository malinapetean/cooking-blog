package com.example.cookingbackend.Repository;

import com.example.cookingbackend.Model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org. springframework. data. domain. Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query(value = """
    SELECT AVG(sub.total_time), MIN(sub.total_time), MAX(sub.total_time), COUNT(*)
    FROM (
        SELECT total_time
        FROM recipe
        ORDER BY id
        LIMIT 1000
    ) AS sub
    """, nativeQuery = true)
    Object getRecipeStatsLimited();
    @Query("SELECT r FROM Recipe r")
    Page<Recipe> findLimitedRecipes(Pageable pageable);



}

