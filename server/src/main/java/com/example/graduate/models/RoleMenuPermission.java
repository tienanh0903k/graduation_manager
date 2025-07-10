
package com.example.graduate.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role_menu_permission")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleMenuPermission {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private boolean canRead = true;
  private boolean canCreate = false;
  private boolean canUpdate = false;
  private boolean canDelete = false;

  @ManyToOne
  @JoinColumn(name = "role_id")
  @JsonIgnore // Ngăn vòng lặp
  private Roles role;

  @ManyToOne
  @JoinColumn(name = "menu_id")
  @JsonIgnore // Ngăn vòng lặp
  private MenuItem menu;

}