package com.example.lifolio.service;

import com.example.lifolio.dto.category.CategoryDTO;
import com.example.lifolio.entity.Category;
import com.example.lifolio.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
class CategorySerivceTest {
    @Autowired
    CategorySerivce categorySerivce;
    @Autowired
    CategoryRepository categoryRepository;

    //SavedID
    private CategoryDTO createCategoryDTO(String testBranch, String testTitle) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setBranch(testBranch);
        categoryDTO.setTitle(testTitle);
        return categoryDTO;
    }

    //Find Category
    private Category findCategory(Long savedId) {
        return categoryRepository.findById(savedId).orElseThrow(IllegalArgumentException::new);
    }


//    @Test
//    public void 카테고리_추가_테스트() {
//        //given
//        CategoryDTO categoryDTO = createCategoryDTO("TestBranch", "TestTitle");
//        Long savedId = categorySerivce.createCategory(categoryDTO);
//
//        //when
//        Category category = findCategory(savedId);
//
//        //then
//        assertThat(category.getTitle()).isEqualTo("TestCode");
//
//    }
//
//    @Test
//    public void 카테고리_업데이트_테스트 () {
//        //given
//        CategoryDTO categoryDTO = createCategoryDTO("TestBranch", "TestTitle");
//        Long savedId = categorySerivce.createCategory(categoryDTO);
//
//        Category category = findCategory(savedId);
//        CategoryDTO targetCategory = new CategoryDTO(category);
//        targetCategory.setBranch("UpdateBranch");
//        targetCategory.setTitle("UpdateCategory");
//        targetCategory.setColorId(0L);
//
//        //when
//        Long updateId = categorySerivce.updateCategory(0L, targetCategory);
//        Category updatedCategory = findCategory(updateId);
//
//        //then
//        assertThat(updatedCategory.getTitle()).isEqualTo("UpdateCategory");
//    }
//
//    @Test
//    public void 카테고리_삭제_테스트 () {
//        //given
//        CategoryDTO categoryDTO = createCategoryDTO("TestBranch", "TestTitle");
//        Long savedId = categorySerivce.createCategory(categoryDTO);
//
//
//        //when
//        categorySerivce.deleteCategory(savedId);
//
//        //then
//        IllegalArgumentException e =
//                assertThrows(IllegalArgumentException.class,
//                        () -> categorySerivce.findCategory(savedId));
//        assertThat(e.getMessage()).isEqualTo("찾는 카테고리 없습니다.");
//
//    }

}