package com.example.graduate.repositories;

import com.example.graduate.models.MenuItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// MenuItemRepository kế thừa JpaRepository để dễ dàng tương tác với cơ sở dữ liệu
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByLabel(String label);

}
