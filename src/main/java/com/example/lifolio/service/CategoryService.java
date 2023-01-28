package com.example.lifolio.service;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.converter.CategoryConvertor;
import com.example.lifolio.dto.category.CategoryReq;
import com.example.lifolio.dto.category.CategoryRes;
import com.example.lifolio.dto.category.SubCategoryReq;
import com.example.lifolio.entity.Category;
import com.example.lifolio.entity.Color;
import com.example.lifolio.entity.SubCategory;
import com.example.lifolio.entity.User;
import com.example.lifolio.repository.CategoryRepository;
import com.example.lifolio.repository.ColorRepository;
import com.example.lifolio.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.lifolio.base.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ColorRepository colorRepository;
    private final UserService userService;


    public List<CategoryRes.Category> getCategoryList(Long userId) {
        List<Category> category = categoryRepository.findByUserId(userId);
        List<CategoryRes.Category> categoryList = new ArrayList<>();
        for (Category value : category) {
            List<CategoryRes.SubCategory> subCategoryArray = getSubCategoryList(value.getId());
            Optional<Color> color =  colorRepository.findById(value.getColorId());
            CategoryRes.Category categoryInfo = CategoryConvertor.CategoryListBuilder(value.getId(), value.getTitle(), color.get().getColorName(),subCategoryArray);
            categoryList.add(categoryInfo);
        }

        return categoryList;
    }

    private List<CategoryRes.SubCategory> getSubCategoryList(Long id) {
        List<CategoryRes.SubCategory> subCategoryArray = new ArrayList<>();
        List<SubCategory> subCategoryList = subCategoryRepository.findByCategoryId(id);
        for (SubCategory category : subCategoryList) {
            if (category.getCategoryId().equals(id)) {
                CategoryRes.SubCategory subCategory = new CategoryRes.SubCategory(category.getId(), category.getTitle());
                subCategoryArray.add(subCategory);
            }
        }
        return subCategoryArray;
    }

    public List<CategoryRes.CategoryIdTitle> getCategoryIdTitleList(Long userId) {
        List<Category> category = categoryRepository.findByUserId(userId);
        List<CategoryRes.CategoryIdTitle> categoryIdTitleList = new ArrayList<>();
        for(Category value : category) {
            CategoryRes.CategoryIdTitle categoryInfo = CategoryConvertor.CategoryIdTitleBuilder(value.getId(), value.getTitle());
            categoryIdTitleList.add(categoryInfo);
        }
        return categoryIdTitleList;
    }

    public void setCategoryList(Long id, CategoryReq.UpdateCategoryReq updateCategoryReq) {
        User user = userService.findNowLoginUser();
        Category category = categoryRepository.getOne(id);
        category.updateCategory(user.getId(), updateCategoryReq.getColorId(), updateCategoryReq.getTitle());
        categoryRepository.save(category);
    }

    public void setCategoryAddSubCategoryList(Long id, CategoryReq.UpdateCategoryAddSubCategoryReq updateCategoryAddSubCategoryReq) {
        User user = userService.findNowLoginUser();
        Category category = categoryRepository.getOne(id);
        category.updateCategory(user.getId(), updateCategoryAddSubCategoryReq.getColorId(), updateCategoryAddSubCategoryReq.getTitle());
        categoryRepository.save(category);

        for(String Subtitle : updateCategoryAddSubCategoryReq.getSubtitle()) {
            SubCategory saveSubCategory = SubCategory.builder()
                    .categoryId(id)
                    .title(Subtitle)
                    .build();
//            saveSubCategory.updateSubCategory(id, Subtitle);
            subCategoryRepository.save(saveSubCategory);
        }
    }


    public void setSubCategoryList(Long id, SubCategoryReq.UpdateSubCategoryReq updateSubCategoryReq) {
        User user = userService.findNowLoginUser();
        SubCategory subCategory = subCategoryRepository.getOne(id);
        subCategory.updateSubCategory(updateSubCategoryReq.getCategoryId(), updateSubCategoryReq.getTitle());
        subCategoryRepository.save(subCategory);
    }

    public void setSubCategoryToCategoryList(Long id, SubCategoryReq.MoveSubCategoryReq moveSubCategoryReq) {
        User user = userService.findNowLoginUser();
        SubCategory subCategory = subCategoryRepository.getOne(id);
        Category saveCategory = Category.builder()
                .userId(user.getId())
                .colorId(moveSubCategoryReq.getColorId())
                .title(moveSubCategoryReq.getTitle())
                .build();
        categoryRepository.save(saveCategory);
        subCategoryRepository.deleteById(subCategory.getId());
    }

    private SubCategory findCategory(Long categoryId) {
        return subCategoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
    }

    public void deleteCategoryList(Long id) throws BaseException {
        Optional<Category> category = categoryRepository.findById(id);
        if(!category.isPresent()) {
            throw new BaseException(NOT_EXIST_CATEGORY);
        }
        List<SubCategory> subCategoryList = subCategoryRepository.findByCategoryId(id);
        for (SubCategory value : subCategoryList) {
            if (value.getCategoryId().equals(id)) {
                deleteSubCategoryList(value.getId());
            }
        }
        categoryRepository.deleteById(category.get().getId());
    }

    public void deleteSubCategoryList(Long id) throws BaseException {
        Optional<SubCategory> subcategory = subCategoryRepository.findById(id);
        if(!subcategory.isPresent()) {
            throw new BaseException(NOT_EXIST_SUBCATEGORY);
        }
        subCategoryRepository.deleteById(subcategory.get().getId());
    }


    public void addCategoryList(CategoryReq.AddCategoryReq addCategoryReq) {
        User user = userService.findNowLoginUser();

        Category saveCategory = Category.builder()
                .userId(user.getId())
                .colorId(addCategoryReq.getColorId())
                .title(addCategoryReq.getTitle())
                .build();

        categoryRepository.save(saveCategory);
    }


    public void addSubCategoryList(SubCategoryReq.AddSubCategoryReq addSubCategoryReq) {
        User user = userService.findNowLoginUser();
        List<CategoryRes.CategoryIdTitle> categoryIdTitleList = new ArrayList<>();

        SubCategory saveSubCategory = SubCategory.builder()
                .categoryId(addSubCategoryReq.getCategoryId())
                .title(addSubCategoryReq.getTitle())
                .build();

        subCategoryRepository.save(saveSubCategory);
    }

    public void addCategorySubCategoryList(CategoryReq.AddCategorySubCategoryReq addCategorySubCategoryReq){
        User user = userService.findNowLoginUser();
        Category saveCategory = Category.builder()
            .userId(user.getId())
            .colorId(addCategorySubCategoryReq.getColorId())
            .title(addCategorySubCategoryReq.getTitle())
            .build();

        categoryRepository.save(saveCategory);

        for(String Subtitle : addCategorySubCategoryReq.getSubtitle()) {
            SubCategory saveSubCategory = SubCategory.builder()
                    .categoryId(saveCategory.getId())
                    .title(Subtitle)
                    .build();
            subCategoryRepository.save(saveSubCategory);
        }

}

    public CategoryRes.CategoryUpdateView getCategoryUpdateView(Long categoryId) throws BaseException {
        Optional<Category> category=categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new BaseException(NOT_EXIST_CATEGORY);
        }
        Optional<Color> color = colorRepository.findById(category.get().getColorId());
        List<String> subcategory=getSubCategoryListByCategoryId(categoryId);
        return CategoryConvertor.CategoryUpdateView(category,color,subcategory);
    }


    public List<String> getSubCategoryListByCategoryId(Long categoryId) {
        List<SubCategoryRepository.CategoryList> categoryListResult=subCategoryRepository.getSubCategoryList(categoryId);
        List<CategoryRes.CategoryList> categoryList=new ArrayList<>();
        categoryListResult.forEach(
                result -> {
                    categoryList.add(new CategoryRes.CategoryList(
                            result.getCategory()
                    ));
                }
        );
        List<String> category=categoryList.stream().map(e-> e.getCategory()).collect(Collectors.toList());
        return category;
    }

    public CategoryRes.SubCategoryUpdateView getSubCategoryUpdateView(Long categoryId) throws BaseException {
        Optional<SubCategory> subCategory=subCategoryRepository.findById(categoryId);
        if(!subCategory.isPresent()){
            throw new BaseException(NOT_EXIST_CATEGORY);
        }
        Optional<Category> category= categoryRepository.findById(subCategory.get().getCategoryId());

        CategoryRes.SubCategoryUpdateView subCategoryUpdateView=CategoryConvertor.SubCategoryUpdateView(subCategory.get(),category.get());
        return subCategoryUpdateView;
    }



}

