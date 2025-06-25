package com.example.graduate.mapper;

import com.example.graduate.dto.UserDTO;
import com.example.graduate.models.Users;

public class UserMapper {

    public static UserDTO ConvertUser(Users user) {
        if (user == null) return null;

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole().getName().name()); // enum to String

        return userDTO;
    }
}
