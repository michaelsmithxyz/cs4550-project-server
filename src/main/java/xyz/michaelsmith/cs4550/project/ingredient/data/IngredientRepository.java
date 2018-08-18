package xyz.michaelsmith.cs4550.project.ingredient.data;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.michaelsmith.cs4550.project.ingredient.data.entity.Ingredient;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByNameContainingIgnoreCase(String search);
}
