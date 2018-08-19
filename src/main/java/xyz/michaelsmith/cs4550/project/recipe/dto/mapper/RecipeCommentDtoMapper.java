package xyz.michaelsmith.cs4550.project.recipe.dto.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.michaelsmith.cs4550.project.common.dto.mapper.DtoMapper;
import xyz.michaelsmith.cs4550.project.recipe.data.entity.RecipeComment;
import xyz.michaelsmith.cs4550.project.recipe.dto.RecipeCommentDto;
import xyz.michaelsmith.cs4550.project.user.data.entity.User;
import xyz.michaelsmith.cs4550.project.user.dto.UserDto;

@Component
public class RecipeCommentDtoMapper implements DtoMapper<RecipeComment, RecipeCommentDto> {
    private final DtoMapper<User, UserDto> userDtoMapper;

    @Autowired
    public RecipeCommentDtoMapper(DtoMapper<User, UserDto> userDtoMapper) {
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public RecipeCommentDto map(RecipeComment entity) {
        RecipeCommentDto dto = new RecipeCommentDto();
        dto.setAuthor(userDtoMapper.map(entity.getAuthor()));
        dto.setPosted(entity.getPosted());
        dto.setText(entity.getText());
        return dto;
    }
}
