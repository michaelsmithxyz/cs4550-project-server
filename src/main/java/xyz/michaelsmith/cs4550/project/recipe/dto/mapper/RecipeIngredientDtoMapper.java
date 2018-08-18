package xyz.michaelsmith.cs4550.project.recipe.dto.mapper;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.michaelsmith.cs4550.project.common.dto.mapper.DtoMapper;
import xyz.michaelsmith.cs4550.project.recipe.data.entity.RecipeIngredientMap;
import xyz.michaelsmith.cs4550.project.recipe.dto.RecipeIngredientDto;

@Component
public class RecipeIngredientDtoMapper implements DtoMapper<RecipeIngredientMap, RecipeIngredientDto> {
    private final BoundMapperFacade<RecipeIngredientMap, RecipeIngredientDto> mapperFacade;

    @Autowired
    public RecipeIngredientDtoMapper(MapperFactory mapperFactory) {
        mapperFactory.classMap(RecipeIngredientMap.class, RecipeIngredientDto.class)
                        .field("ingredient.id", "ingredientId")
                        .field("ingredient.name", "ingredient")
                        .byDefault().register();
        mapperFacade = mapperFactory.getMapperFacade(RecipeIngredientMap.class, RecipeIngredientDto.class);
    }

    @Override
    public RecipeIngredientDto map(RecipeIngredientMap entity) {
        return mapperFacade.map(entity);
    }
}
