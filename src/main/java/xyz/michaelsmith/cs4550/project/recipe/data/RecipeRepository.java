package xyz.michaelsmith.cs4550.project.recipe.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.michaelsmith.cs4550.project.recipe.data.entity.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT DISTINCT r FROM Recipe r INNER JOIN r.ingredients im INNER JOIN im.ingredient i WHERE i.id = :ingredientId")
    List<Recipe> searchByIngredient(@Param("ingredientId") Long ingredientId);
}
