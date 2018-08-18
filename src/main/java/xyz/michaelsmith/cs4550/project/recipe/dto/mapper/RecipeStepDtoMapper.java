package xyz.michaelsmith.cs4550.project.recipe.dto.mapper;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.michaelsmith.cs4550.project.common.dto.mapper.DtoMapper;
import xyz.michaelsmith.cs4550.project.recipe.data.entity.RecipeStep;
import xyz.michaelsmith.cs4550.project.recipe.dto.RecipeStepDto;

@Component
public class RecipeStepDtoMapper implements DtoMapper<RecipeStep, RecipeStepDto> {
    private final BoundMapperFacade<RecipeStep, RecipeStepDto> mapperFacade;

    @Autowired
    public RecipeStepDtoMapper(MapperFactory mapperFactory) {
        mapperFactory.classMap(RecipeStep.class, RecipeStepDto.class).byDefault().register();
        mapperFacade = mapperFactory.getMapperFacade(RecipeStep.class, RecipeStepDto.class);
    }

    @Override
    public RecipeStepDto map(RecipeStep entity) {
        return mapperFacade.map(entity);
    }
}
