// package com.example.graduate.controller;

// import java.util.stream.Collectors;
// import java.util.List;

// import org.springframework.http.ResponseEntity;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.graduate.dto.CategoriesDTO;
// import com.example.graduate.models.Category;
// import com.example.graduate.service.interfaces.ICategoriesService;

// import jakarta.validation.Valid;

// // import org.springframework.web.bind.annotation.RequestParam;

// @RestController
// @RequestMapping("/api/v1/categories")
// public class CategoriesController {
//     private final ICategoriesService categoriesService;

//     public CategoriesController(ICategoriesService categoriesService) {
//         this.categoriesService = categoriesService;
//     }

//     @GetMapping
//     public List<Category> getAllCategories() {
//         return categoriesService.getAllCategories();
//     }

//     // @GetMapping("")
//     // public ResponseEntity<String> getCategoriesParams(
//     //         @RequestParam(name = "page", required = false) String page,
//     //         @RequestParam(name = "limit", required = false) String limit) {
//     //     return ResponseEntity.ok(String.format("Đây là dữ liệu các em %s %s", page, limit));
//     // }

//     @PostMapping("")
//     public ResponseEntity<?> insertCategory(
//             @Valid @RequestBody CategoriesDTO categoryDTO,
//             BindingResult result) {

//         if (result.hasErrors()) {
//             List<String> errorMessages = result.getFieldErrors()
//                     .stream()
//                     .map(fieldError -> fieldError.getDefaultMessage())
//                     .collect(Collectors.toList());

//             return ResponseEntity.badRequest().body(errorMessages);
//         }

//         return ResponseEntity.ok("This is insertCategory: " + categoryDTO);
//     }

// }
