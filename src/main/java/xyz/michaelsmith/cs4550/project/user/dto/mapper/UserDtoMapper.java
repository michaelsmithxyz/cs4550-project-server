package xyz.michaelsmith.cs4550.project.user.dto.mapper;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.michaelsmith.cs4550.project.common.dto.mapper.DtoMapper;
import xyz.michaelsmith.cs4550.project.user.data.entity.User;
import xyz.michaelsmith.cs4550.project.user.dto.UserDto;

@Component
public class UserDtoMapper implements DtoMapper<User, UserDto> {
    private final BoundMapperFacade<User, UserDto> mapperFacade;

    @Autowired
    public UserDtoMapper(MapperFactory mapperFactory) {
        mapperFactory.classMap(User.class, UserDto.class).byDefault().register();
        mapperFacade = mapperFactory.getMapperFacade(User.class, UserDto.class);
    }

    @Override
    public UserDto map(User entity) {
        return mapperFacade.map(entity);
    }
}
