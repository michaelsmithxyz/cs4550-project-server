package xyz.michaelsmith.cs4550.project.recipe.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping   ;
import org.springframework.web.bind.annotation.RestController;
import xyz.michaelsmith.cs4550.project.common.api.exception.ResourceNotFoundException;
import xyz.michaelsmith.cs4550.project.recipe.dto.RecipeDto;
import xyz.michaelsmith.cs4550.project.recipe.service.RecipeService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/recipe")
public class RecipeApiController {
    private final RecipeService recipeService;

    public RecipeApiController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(path = {"", "/"},
                    method = GET,
                    produces = APPLICATION_JSON_VALUE)
    public List<RecipeDto> getAll() {
        return recipeService.getAll();
    }

    @RequestMapping(path = "/{recipeId}",
                    method = GET,
                    produces = APPLICATION_JSON_VALUE)
    public RecipeDto getById(@PathVariable("recipeId") Long recipeId) {
        try {
            return recipeService.getRecipeById(recipeId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(path = {"", "/"},
                    method = POST,
                    consumes = APPLICATION_JSON_VALUE,
                    produces = APPLICATION_JSON_VALUE)
    public RecipeDto createRecipe(@RequestBody RecipeDto recipeDto) {
        try {
            return recipeService.createRecipe(recipeDto);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(path = "/{recipeId}",
                    method = PUT,
                    consumes = APPLICATION_JSON_VALUE,
                    produces = APPLICATION_JSON_VALUE)
    public RecipeDto updateRecipe(@PathVariable("recipeId") Long recipeId, @RequestBody RecipeDto recipeDto) {
        try {
            return recipeService.updateRecipe(recipeId, recipeDto);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(path = "/{recipeId}",
                    method = DELETE)
    public void deleteRecipe(@PathVariable("recipeId") Long recipeId) {
        recipeService.deleteRecipe(recipeId);
    }
}
