package com.example.pizza_service.dto;
import com.example.pizza_service.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Integer id;
    private String username;
    private String name;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setName(name);
        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());
        return userDto;
    }
}
