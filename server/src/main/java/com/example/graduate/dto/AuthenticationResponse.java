package com.example.graduate.dto;

import java.util.List;

import com.example.graduate.dto.MenuPermission.MenuPermissionResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
  private String token;
  private UserDTO userDto;
  private List<MenuPermissionResponseDTO> menuPermissions;
  private String message;
}
