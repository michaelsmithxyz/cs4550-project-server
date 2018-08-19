package xyz.michaelsmith.cs4550.project.recipe.api;

import org.springframework.web.bind.annotation.*;
import xyz.michaelsmith.cs4550.project.common.api.exception.ResourceNotFoundException;
import xyz.michaelsmith.cs4550.project.recipe.dto.RecipeCommentDto;
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

    @RequestMapping(path = {"", "/"},
                    method = GET,
                    params = {"title"},
                    produces = APPLICATION_JSON_VALUE)
    public List<RecipeDto> searchByTitle(@RequestParam("title") String title) {
        return recipeService.searchRecipesByTitle(title);
    }

    @RequestMapping(path = {"", "/"},
                    method = GET,
                    params = {"ingredient"},
                    produces = APPLICATION_JSON_VALUE)
    public List<RecipeDto> searchByIngredient(@RequestParam("ingredient") Long ingredient) {
        try {
            return recipeService.searchRecipesByIngredient(ingredient);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(path = {"", "/"},
            method = GET,
            params = {"author"},
            produces = APPLICATION_JSON_VALUE)
    public List<RecipeDto> searchByAuthor(@RequestParam("author") Long userId) {
        try {
            return recipeService.searchRecipesByAuthor(userId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
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

    @RequestMapping(path = "/{recipeId}/comments",
                    method = POST,
                    consumes = APPLICATION_JSON_VALUE,
                    produces = APPLICATION_JSON_VALUE)
    public RecipeDto createComment(@PathVariable("recipeId") Long recipeId, @RequestBody RecipeCommentDto commentDto) {
        try {
            return recipeService.createComment(recipeId, commentDto);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }
}
