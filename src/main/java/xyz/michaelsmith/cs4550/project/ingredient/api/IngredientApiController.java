package xyz.michaelsmith.cs4550.project.ingredient.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import xyz.michaelsmith.cs4550.project.common.api.exception.ResourceNotFoundException;
import xyz.michaelsmith.cs4550.project.ingredient.dto.IngredientDto;
import xyz.michaelsmith.cs4550.project.ingredient.service.IngredientService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientApiController {
    private final IngredientService ingredientService;

    @Autowired
    public IngredientApiController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @RequestMapping(path = "/{ingredientId}",
                    method = GET,
                    produces = APPLICATION_JSON_VALUE)
    public IngredientDto getIngredient(@PathVariable("ingredientId") Long ingredientId) {
        try {
            return ingredientService.getIngredient(ingredientId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(path = {"", "/"},
                    method = GET,
                    params = {"search"},
                    produces = APPLICATION_JSON_VALUE)
    public List<IngredientDto> searchIngredients(@RequestParam("search") String query) {
        return ingredientService.searchIngredients(query);
    }
}
