package xyz.michaelsmith.cs4550.project.recipe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.michaelsmith.cs4550.project.common.dto.mapper.DtoMapper;
import xyz.michaelsmith.cs4550.project.ingredient.dto.IngredientDto;
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
import java.util.Date;
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

    public List<RecipeDto> searchRecipesByTitle(String title) {
        return recipeRepository.findByTitleContainingIgnoreCase(title).stream().map(recipeDtoMapper::map).collect(toList());
    }

    public List<RecipeDto> searchRecipesByIngredient(Long ingredientId) {
        IngredientDto ingredient = ingredientService.getIngredient(ingredientId);
        return recipeRepository.searchByIngredient(ingredient.getId()).stream().map(recipeDtoMapper::map).collect(toList());
    }

    public RecipeDto createRecipe(RecipeDto recipeDto) {
        User currentUser = userService.getUserEntity();

        Recipe recipe = new Recipe();
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setImage(recipeDto.getImage());
        recipe.setAuthor(currentUser);
        recipe.setDuration(recipeDto.getDuration());
        recipe.setYield(recipeDto.getYield());
        recipe.setCreated(new Date());

        buildRecipeIngredients(recipe, recipeDto);

        recipe.setSteps(new ArrayList<>());
        buildRecipeSteps(recipe, recipeDto);

        return recipeDtoMapper.map(recipeRepository.save(recipe));
    }

    public RecipeDto updateRecipe(Long recipeId, RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RECIPE_NOT_FOUND);
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setImage(recipeDto.getImage());
        recipe.setDuration(recipeDto.getDuration());
        recipe.setYield(recipeDto.getYield());

        recipe.getSteps().clear();
        buildRecipeSteps(recipe, recipeDto);

        recipe.getIngredients().clear();
        buildRecipeIngredients(recipe, recipeDto);

        return recipeDtoMapper.map(recipeRepository.save(recipe));
    }

    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    private static void buildRecipeSteps(Recipe recipe, RecipeDto recipeDto) {
        for (int i = 0; i < recipeDto.getSteps().size(); i++) {
            RecipeStepDto stepDto = recipeDto.getSteps().get(i);
            RecipeStep step = new RecipeStep();
            step.setText(stepDto.getText());
            step.setSortOrder(i);
            recipe.addStep(step);
        }
    }

    private void buildRecipeIngredients(Recipe recipe, RecipeDto recipeDto) {
        recipeDto.getIngredients().forEach(ingredientDto -> {
            RecipeIngredientMap ingredientMap = new RecipeIngredientMap();
            ingredientMap.setIngredient(ingredientService.getIngredientEntity(ingredientDto.getIngredientId()));
            ingredientMap.setModifier(ingredientDto.getModifier());
            ingredientMap.setQuantity(ingredientDto.getQuantity());
            recipe.addIngredient(ingredientMap);
        });
    }
}
