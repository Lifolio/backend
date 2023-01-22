package com.example.lifolio.converter;

import com.example.lifolio.dto.category.CategoryRes;
import com.example.lifolio.entity.Category;
import com.example.lifolio.entity.Color;
import com.example.lifolio.entity.SubCategory;

import java.util.List;
import java.util.Optional;

public class CategoryConvertor {
    public static CategoryRes.Category CategoryListBuilder(Long id, String title, String colorName, List<CategoryRes.SubCategory> subCategoryArray) {
        return CategoryRes.Category.builder().categoryId(id).categoryName(title).categoryColor(colorName).subCategoryList(subCategoryArray).build();
    }

    public static CategoryRes.CategoryIdTitle CategoryIdTitleBuilder(Long id, String title){
        return CategoryRes.CategoryIdTitle.builder().categoryId(id).categoryName(title).build();
    }

    public static CategoryRes.CategoryUpdateView CategoryUpdateView(Optional<Category> category, Optional<Color> color, List<String> subcategory) {
        return CategoryRes.CategoryUpdateView.builder().categoryId(category.get().getId())
                .categoryName(category.get().getTitle())
                .colorName(color.get().getColorName())
                .subCategoryList(subcategory).build();
    }

    public static CategoryRes.SubCategoryUpdateView SubCategoryUpdateView(SubCategory subCategory, Category category) {
        return CategoryRes.SubCategoryUpdateView.builder().categoryId(category.getId())
                .categoryName(category.getTitle())
                .subCategoryId(subCategory.getId())
                .subCategoryName(subCategory.getTitle()).build();
    }
}
