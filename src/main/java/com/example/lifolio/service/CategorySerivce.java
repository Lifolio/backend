package com.example.lifolio.service;

import com.example.lifolio.dto.category.CategoryDTO;
import com.example.lifolio.entity.Category;
import com.example.lifolio.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class CategorySerivce {
    private final CategoryRepository categoryRepository;

    public Long createCategory(CategoryDTO categoryDTO) {

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
        } //중,소분류 등록

       return categoryRepository.save(category).getId();
    }

    public Map<String, CategoryDTO> readCategory (String branch) {
        Category category = categoryRepository.findByBranchAndTitle(branch, "ROOT")
                .orElseThrow(() -> new IllegalArgumentException("찾는 대분류가 없습니다"));

        CategoryDTO categoryDTO = new CategoryDTO(category);

        Map <String, CategoryDTO> data = new HashMap<>();
        data.put(categoryDTO.getTitle(), categoryDTO);

        return data;
    } //branch로 검색했을 때 가장 최상단부터 최하단까지 조회

    public Long updateCategory (Long categoryId,CategoryDTO categoryDTO) {
        Category category = findCategory(categoryId);
        category.setTitle(categoryDTO.getTitle());

        return category.getId();
    }

    public void deleteCategory (Long categoryId) {
        Category category = findCategory(categoryId);

        if (category.getSubCategory().size() == 0) { //하위 카테고리 없으면
            Category parentCategory = findCategory(category.getParentCategory().getId());
            if (!parentCategory.getTitle().equals("ROOT")) { //ROOT가 아닌 다른 부모일 경우
                parentCategory.getSubCategory().remove(category);
            }
            categoryRepository.deleteById(category.getId());
        } else { //하위 카테고리 있으면
            Category parentCategory = findCategory(category.getParentCategory().getId());
            if(!parentCategory.getTitle().equals("ROOT")) {
                parentCategory.getSubCategory().remove(category);
            }
            category.setTitle("Deleted category");
        }
    }

    private Category findCategory (Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
    }

}

