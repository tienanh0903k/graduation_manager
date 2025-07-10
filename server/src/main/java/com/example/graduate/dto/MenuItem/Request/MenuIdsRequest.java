package com.example.graduate.dto.MenuItem.Request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuIdsRequest {
    private List<Long> menuIds;
}