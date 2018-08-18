package xyz.michaelsmith.cs4550.project.ingredient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.michaelsmith.cs4550.project.common.dto.mapper.DtoMapper;
import xyz.michaelsmith.cs4550.project.ingredient.data.IngredientRepository;
import xyz.michaelsmith.cs4550.project.ingredient.data.entity.Ingredient;
import xyz.michaelsmith.cs4550.project.ingredient.dto.IngredientDto;

import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final DtoMapper<Ingredient, IngredientDto> ingredientDtoMapper;
    private static final Supplier<? extends RuntimeException> INGREDIENT_NOT_FOUND = () -> new IllegalArgumentException("Ingredient not found for ID");

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository, DtoMapper<Ingredient, IngredientDto> ingredientDtoMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientDtoMapper = ingredientDtoMapper;
    }

    public IngredientDto getIngredient(Long ingredientId) {
        return ingredientRepository.findById(ingredientId).map(ingredientDtoMapper::map).orElseThrow(INGREDIENT_NOT_FOUND);
    }

    public Ingredient getIngredientEntity(Long ingredientId) {
        return ingredientRepository.findById(ingredientId).orElseThrow(INGREDIENT_NOT_FOUND);
    }

    public List<IngredientDto> searchIngredients(String query) {
        return ingredientRepository.findByNameContainingIgnoreCase(query).stream().map(ingredientDtoMapper::map).collect(toList());
    }

    public IngredientDto createIngredient(IngredientDto ingredientDto) {
        Ingredient ingredient = ingredientDtoMapper.mapReverse(ingredientDto);
        if (ingredient.getId() != null) {
            throw new IllegalArgumentException("Cannot create ingredient with an id");
        }
        return ingredientDtoMapper.map(ingredientRepository.save(ingredient));
    }
}
