package xyz.michaelsmith.cs4550.project.recipe.data;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.michaelsmith.cs4550.project.recipe.data.entity.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByTitleContainingIgnoreCase(String title);
}
