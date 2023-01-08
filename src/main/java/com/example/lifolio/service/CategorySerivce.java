package com.example.lifolio.service;

import com.example.lifolio.dto.category.CategoryDTO;
import com.example.lifolio.entity.Category;
import com.example.lifolio.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategorySerivce {
    private final CategoryRepository categoryRepository;

    public Long saveCategory(CategoryDTO categoryDTO) {

        Category category = categoryDTO.toEntity();

        //대분류 등록
        if (categoryDTO.getParentCategoryName() == null) {

            if (categoryRepository.existsByBranchAndTitle(categoryDTO.getBranch(), categoryDTO.getTitle())) {
                throw new RuntimeException("branch와 title이 같을 수 없습니다. ");
            } //branch와 title의 중복값 검사
            
            Category rootCategory = categoryRepository.findByBranchAndTitle(categoryDTO.getBranch(), "ROOT")
                    .orElseGet(() ->
                            Category.builder()
                            .branch(categoryDTO.getBranch())
                            .level(0)
                            .build()
                    );
            category.setParentCategory(rootCategory);
            category.setLevel(1);
        } else {
            String parentCategoryName = categoryDTO.getParentCategoryName();
            Category parentCategory = categoryRepository.findByBranchAndTitle(categoryDTO.getBranch(), parentCategoryName)
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리 없으면 예외"));
            category.setLevel(parentCategory.getLevel() + 1);
            category.setParentCategory(parentCategory);
            parentCategory.getSubCategory().add(category);
        } //중,소분류 등록

       return categoryRepository.save(category).getId();
    }

}

