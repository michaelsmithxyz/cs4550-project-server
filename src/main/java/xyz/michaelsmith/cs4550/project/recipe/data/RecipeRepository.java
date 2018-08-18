package xyz.michaelsmith.cs4550.project.recipe.data;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.michaelsmith.cs4550.project.recipe.data.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
