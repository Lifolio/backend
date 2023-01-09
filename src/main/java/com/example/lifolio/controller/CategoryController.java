package com.example.lifolio.controller;

import com.example.lifolio.dto.category.CategoryDTO;
import com.example.lifolio.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/home")
public class CategoryController {
    private final CategoryService categoryService;

    @ResponseBody
    @PostMapping("/categories")
    public Long createCategory(CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    @ResponseBody
    @GetMapping("/categories/{branch}")
    public Map<String, CategoryDTO> readCategory(@PathVariable String branch) {
        return categoryService.readCategory(branch);
    }

    @ResponseBody
    @PatchMapping("/categories/{id}")
    public Long updateCategory (@PathVariable("categoryId") Long id,
                                CategoryDTO categoryDTO) {
        return categoryService.updateCategory(id, categoryDTO);
    }

    @ResponseBody
    @DeleteMapping("/categories/{id}")
    public void deleteCategory (@PathVariable("categoryId") Long id) {
        categoryService.deleteCategory(id);
    }



}
