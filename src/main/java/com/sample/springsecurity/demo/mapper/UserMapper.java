package com.sample.springsecurity.demo.mapper;

import com.sample.springsecurity.demo.dto.User;
import com.sample.springsecurity.demo.web.presentation.UserDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User convertToUser(UserDto userDto);

    UserDto convertToUserDto(User user);
}
