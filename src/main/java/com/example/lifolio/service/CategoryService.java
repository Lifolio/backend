package com.example.lifolio.service;

import com.example.lifolio.dto.category.CategoryDTO;
import com.example.lifolio.entity.Category;
import com.example.lifolio.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Long createCategory(CategoryDTO categoryDTO) {

        Category category = categoryDTO.toEntity();

        //대분류 등록
        if (categoryDTO.getParentCategoryName() == null) {
            Category rootCategory = categoryRepository.findByBranchAndTitle(categoryDTO.getBranch(), "ROOT")
                    .orElseGet(() ->
                    Category.builder()
                            .branch(categoryDTO.getBranch())
                            .level(0)
                            .build()
            ); //branch의 상위 카테고리 첫 생성 시 ROOT 생성
            category.setParentCategory(rootCategory);
            category.setLevel(1);
        } else {
            String parentCategoryName = categoryDTO.getParentCategoryName();
            Category parentCategory = categoryRepository.findByBranchAndTitle(categoryDTO.getBranch(), parentCategoryName)
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리 없으면 예외"));
            category.setLevel(parentCategory.getLevel() + 1);
            category.setParentCategory(parentCategory);
            parentCategory.getSubCategory().add(category);
        } //소분류 등록

       return categoryRepository.save(category).getId();
    }

    public Map<String, CategoryDTO> readCategory(Long categoryId) {

        Category category = findCategory(categoryId);
        CategoryDTO categoryDTO = new CategoryDTO(category);

        Map <String, CategoryDTO> data = new HashMap<>();
        data.put(categoryDTO.getTitle(), categoryDTO);

        return data;
    }

    public Long updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = findCategory(categoryId);
        category.setTitle(categoryDTO.getTitle());
        category.setColorId(categoryDTO.getColorId());

        return category.getId();
    }

    public Long deleteCategory(Long categoryId) {
        Category category = findCategory(categoryId);

        if (category.getSubCategory().size() == 0) { //소분류이면
            categoryRepository.deleteById(category.getId());
        } else { //소분류 아니면
            Category parentCategory = findCategory(category.getParentCategory().getId());
            parentCategory.getSubCategory().remove(category);
            categoryRepository.deleteById(category.getId());
        }

        return category.getId();
    }

    private Category findCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
    }

}

