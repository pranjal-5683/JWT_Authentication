package com.adminbackend.mapper;

import com.adminbackend.dto.SignUpDto;
import com.adminbackend.dto.UserDto;
import com.adminbackend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto mapToUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User mapToUser(SignUpDto signUpDto);
}
