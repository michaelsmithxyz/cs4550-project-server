package xyz.michaelsmith.cs4550.project.recipe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.michaelsmith.cs4550.project.common.dto.mapper.DtoMapper;
import xyz.michaelsmith.cs4550.project.ingredient.service.IngredientService;
import xyz.michaelsmith.cs4550.project.recipe.data.RecipeRepository;
import xyz.michaelsmith.cs4550.project.recipe.data.entity.Recipe;
import xyz.michaelsmith.cs4550.project.recipe.data.entity.RecipeIngredientMap;
import xyz.michaelsmith.cs4550.project.recipe.data.entity.RecipeStep;
import xyz.michaelsmith.cs4550.project.recipe.dto.RecipeDto;
import xyz.michaelsmith.cs4550.project.recipe.dto.RecipeStepDto;
import xyz.michaelsmith.cs4550.project.user.data.entity.User;
import xyz.michaelsmith.cs4550.project.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

@Service
public class RecipeService {
    private final UserService userService;
    private final IngredientService ingredientService;

    private final RecipeRepository recipeRepository;
    private final DtoMapper<Recipe, RecipeDto> recipeDtoMapper;

    private static final Supplier<? extends RuntimeException> RECIPE_NOT_FOUND = () -> new IllegalArgumentException("Recipe not found for id");

    @Autowired
    public RecipeService(UserService userService, IngredientService ingredientService, RecipeRepository recipeRepository, DtoMapper<Recipe, RecipeDto> recipeDtoMapper) {
        this.userService = userService;
        this.ingredientService = ingredientService;
        this.recipeRepository = recipeRepository;
        this.recipeDtoMapper = recipeDtoMapper;
    }

    public List<RecipeDto> getAll() {
        return recipeRepository.findAll().stream().map(recipeDtoMapper::map).collect(toList());
    }

    public RecipeDto getRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId).map(recipeDtoMapper::map).orElseThrow(RECIPE_NOT_FOUND);
    }

    public RecipeDto createRecipe(RecipeDto recipeDto) {
        User currentUser = userService.getUserEntity();

        Recipe recipe = new Recipe();
        recipe.setTitle(recipeDto.getTitle());
        recipe.setAuthor(currentUser);
        recipe.setDuration(recipeDto.getDuration());
        recipe.setYield(recipeDto.getYield());
        recipeDto.getIngredients().forEach(ingredientDto -> {
            RecipeIngredientMap ingredientMap = new RecipeIngredientMap();
            ingredientMap.setIngredient(ingredientService.getIngredientEntity(ingredientDto.getIngredientId()));
            ingredientMap.setModifier(ingredientDto.getModifier());
            ingredientMap.setQuantity(ingredientDto.getQuantity());
            recipe.addIngredient(ingredientMap);
        });

        recipe.setSteps(new ArrayList<>());
        for (int i = 0; i < recipeDto.getSteps().size(); i++) {
            RecipeStepDto stepDto = recipeDto.getSteps().get(i);
            RecipeStep step = new RecipeStep();
            step.setText(stepDto.getText());
            step.setSortOrder(i);
            recipe.addStep(step);
        }

        return recipeDtoMapper.map(recipeRepository.save(recipe));
    }
}
