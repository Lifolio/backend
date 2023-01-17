package com.example.lifolio.converter;

import com.example.lifolio.dto.category.CategoryRes;

import java.util.List;

public class CategoryConvertor {
    public static CategoryRes.Category CategoryListBuilder(Long id, String title, List<CategoryRes.SubCategory> subCategoryArray) {
        return CategoryRes.Category.builder().categoryId(id).categoryName(title).subCategoryList(subCategoryArray).build();
    }
}
