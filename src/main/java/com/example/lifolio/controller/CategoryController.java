package com.example.lifolio.controller;

import com.example.lifolio.dto.category.CategoryDTO;
import com.example.lifolio.service.CategorySerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/home")
public class CategoryController {
    private final CategorySerivce categorySerivce;

    @ResponseBody
    @PostMapping("/categories")
    public Long createCategory(CategoryDTO categoryDTO) {
        return categorySerivce.createCategory(categoryDTO);
    }

    @ResponseBody
    @GetMapping("/categories/{branch}")
    public Map<String, CategoryDTO> readCategory(@PathVariable String branch) {
        return categorySerivce.readCategory(branch);
    }

    @ResponseBody
    @PatchMapping("/categories/{id}")
    public Long updateCategory (@PathVariable("categoryId") Long id,
                                CategoryDTO categoryDTO) {
        return categorySerivce.updateCategory(id, categoryDTO);
    }

    @ResponseBody
    @DeleteMapping("/categories/{id}")
    public void deleteCategory (@PathVariable("categoryId") Long id) {
        categorySerivce.deleteCategory(id);
    }



}
