package com.nisum.dto.user;

import com.nisum.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toModel(UserRequest request);

    void updateModel(UserRequest request, @MappingTarget User user);
}
