package com.example.graduate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.graduate.dto.TeacherDTO;
import com.example.graduate.dto.UserDTO;
import com.example.graduate.models.Users;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<Users, UserDTO> {

    @Override
    @Mapping(source = "role.name", target = "role")
    UserDTO toDTO(Users entity);


    @Override
    // No property named "roleName" exists in source parameter(s). Did you mean "role"?Java(0)
    @Mapping(source = "role", target = "role.name")
    Users toEntity(UserDTO dto);

    TeacherDTO toTeacherDTO(Users user);
}


// @Mapper(componentModel = "spring")
// public class UserMapper extends BaseMapper<Users, UserDTO> {
//     @Override
//     @Mapping(source = "role.name", target = "roleName")
//     UserDTO toDTO(User entity);

//     @Override
//     @Mapping(source = "roleName", target = "role.name")
//     Users toEntity(UserDTO dto);

//     // public static UserDTO ConvertUser(Users user) {
//     //     if (user == null) return null;

//     //     UserDTO userDTO = new UserDTO();
//     //     userDTO.setId(user.getId());
//     //     userDTO.setName(user.getName());
//     //     userDTO.setEmail(user.getEmail());
//     //     userDTO.setRole(user.getRole().getName().name()); // enum to String

//     //     return userDTO;
//     // }
    
// }
