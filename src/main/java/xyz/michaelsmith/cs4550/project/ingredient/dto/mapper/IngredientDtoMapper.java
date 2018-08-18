package xyz.michaelsmith.cs4550.project.ingredient.dto.mapper;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.michaelsmith.cs4550.project.common.dto.mapper.DtoMapper;
import xyz.michaelsmith.cs4550.project.ingredient.data.entity.Ingredient;
import xyz.michaelsmith.cs4550.project.ingredient.dto.IngredientDto;

@Component
public class IngredientDtoMapper implements DtoMapper<Ingredient, IngredientDto> {
    private final BoundMapperFacade<Ingredient, IngredientDto> mapperFacade;

    @Autowired
    public IngredientDtoMapper(MapperFactory mapperFactory) {
        mapperFactory.classMap(Ingredient.class, IngredientDto.class).byDefault().register();
        mapperFacade = mapperFactory.getMapperFacade(Ingredient.class, IngredientDto.class);
    }

    @Override
    public IngredientDto map(Ingredient entity) {
        return mapperFacade.map(entity);
    }

    @Override
    public Ingredient mapReverse(IngredientDto dto) {
        return mapperFacade.mapReverse(dto);
    }
}
