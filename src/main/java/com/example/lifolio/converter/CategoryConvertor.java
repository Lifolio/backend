package com.example.lifolio.converter;

import com.example.lifolio.dto.category.CategoryRes;

import java.util.List;

public class CategoryConvertor {
    public static CategoryRes.Category CategoryListBuilder(Long id, String title, String colorName, List<CategoryRes.SubCategory> subCategoryArray) {
        return CategoryRes.Category.builder().categoryId(id).categoryName(title).categoryColor(colorName).subCategoryList(subCategoryArray).build();
    }

    public static CategoryRes.CategoryIdTitle CategoryIdTitleBuilder(Long id, String title){
        return CategoryRes.CategoryIdTitle.builder().categoryId(id).categoryName(title).build();
    }
}
