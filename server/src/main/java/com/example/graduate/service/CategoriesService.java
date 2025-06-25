// package com.example.graduate.service;

// import org.springframework.stereotype.Service;

// import com.example.graduate.models.Category;
// import com.example.graduate.repositories.CategoryRepository;
// import com.example.graduate.service.interfaces.ICategoriesService;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class CategoriesService implements ICategoriesService {
//     private final CategoryRepository categoryRepository;

//     public CategoriesService(CategoryRepository categoryRepository) {
//         this.categoryRepository = categoryRepository;
//     }


//     @Override
//     public List<Category> getAllCategories() {
//         return this.categoryRepository.findAll();
//     }
// }