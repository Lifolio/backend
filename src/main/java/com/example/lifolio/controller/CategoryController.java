package com.example.lifolio.controller;

import com.example.lifolio.dto.category.CategoryDTO;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/home")
public class CategoryController {
    private final CategoryService categoryService;
    private final TokenProvider jwtProvider;

    @ResponseBody
    @PostMapping("/categories")
    public Long createCategory(CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    @ResponseBody
    @GetMapping("/categories/{branch}")
    public Map<String, CategoryDTO> readCategory(@PathVariable("categoryId") Long id) {
        return categoryService.readCategory(id);
    }

    @ResponseBody
    @PatchMapping("/categories/{id}")
    public Long updateCategory (@PathVariable("categoryId") Long id,
                                CategoryDTO categoryDTO) {
        return categoryService.updateCategory(id, categoryDTO);
    }

    @ResponseBody
    @DeleteMapping("/categories/{id}")
    public Long deleteCategory (@PathVariable("categoryId") Long id) {
        return categoryService.deleteCategory(id);
    }


}
