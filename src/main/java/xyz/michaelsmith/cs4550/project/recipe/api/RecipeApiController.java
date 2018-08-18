package xyz.michaelsmith.cs4550.project.recipe.api;

import org.springframework.web.bind.annotation.RequestMapping   ;
import org.springframework.web.bind.annotation.RestController;
import xyz.michaelsmith.cs4550.project.recipe.dto.RecipeDto;
import xyz.michaelsmith.cs4550.project.recipe.service.RecipeService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

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
}
