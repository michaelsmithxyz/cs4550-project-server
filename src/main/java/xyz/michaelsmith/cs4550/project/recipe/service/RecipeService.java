package xyz.michaelsmith.cs4550.project.recipe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.michaelsmith.cs4550.project.common.dto.mapper.DtoMapper;
import xyz.michaelsmith.cs4550.project.recipe.data.RecipeRepository;
import xyz.michaelsmith.cs4550.project.recipe.data.entity.Recipe;
import xyz.michaelsmith.cs4550.project.recipe.dto.RecipeDto;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final DtoMapper<Recipe, RecipeDto> recipeDtoMapper;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, DtoMapper<Recipe, RecipeDto> recipeDtoMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeDtoMapper = recipeDtoMapper;
    }

    public List<RecipeDto> getAll() {
        return recipeRepository.findAll().stream().map(recipeDtoMapper::map).collect(toList());
    }
}
