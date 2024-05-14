package com.demo.UserApi.utils;

import com.demo.UserApi.models.dto.UserDto;
import com.demo.UserApi.models.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component

public class UserUtil {

    public UserDto userToDto (User user){
        return new UserDto(user.getId(), user.getUserName(), user.getPassword(), user.getPersonId());
    }

    public User dtoToUser (UserDto userDto){
        return new User(userDto.getId(), userDto.getUserName(), userDto.getPassword(), userDto.getPersonId());
    }

    public List<UserDto> userMapper(List<User> users){
        List<UserDto> userDto = new ArrayList<>();
        for (User user : users) {
            userDto.add(userToDto(user));
        }
        return userDto;
    }

}
