package com.ceshiren.mini.converter;



import com.ceshiren.mini.dto.UserDto;
import com.ceshiren.mini.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface UserConverter {
//    UserDto ---->  User

    @Mappings({
            @Mapping(target = "id",source = "id"),
            @Mapping(target = "name",source = "username"),
            @Mapping(target = "password",source = "password"),
            @Mapping(target = "email",source = "email")
    })
    public User userDtoForUser(UserDto userDto);



    @Mappings({
            @Mapping(target = "id",source = "id"),
            @Mapping(target = "username",source = "name"),
            @Mapping(target = "password",source = "password"),
            @Mapping(target = "email",source = "email")
    })
    public UserDto userForUserDto(User user);
}
