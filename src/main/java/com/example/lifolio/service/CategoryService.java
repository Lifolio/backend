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

import static com.example.lifolio.base.BaseResponseStatus.NOT_EXIST_CATEGORY;

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

    public void setSubCategoryList(Long id, SubCategoryReq.UpdateSubCategoryReq updateSubCategoryReq) {
        User user = userService.findNowLoginUser();
        SubCategory subCategory = subCategoryRepository.getOne(id);
        subCategory.updateSubCategory(updateSubCategoryReq.getCategoryId(), updateSubCategoryReq.getTitle());
        subCategoryRepository.save(subCategory);
    } //소분류 카테고리 내용 수정

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
    } //소분류 카테고리->대분류 카테고리로 이동

    private SubCategory findCategory(Long categoryId) {
        return subCategoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
    }

    public void deleteCategoryList(Long id) {
        Category category = categoryRepository.getOne(id);
        List<SubCategory> subCategoryList = subCategoryRepository.findByCategoryId(id);
        for (SubCategory value : subCategoryList) {
            if (value.getCategoryId().equals(id)) {
                deleteSubCategoryList(value.getId());
            }
        }
        categoryRepository.deleteById(category.getId());
    }

    public void deleteSubCategoryList(Long id) {
        SubCategory subcategory = subCategoryRepository.getOne(id);
        subCategoryRepository.deleteById(subcategory.getId());
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

        SubCategory saveSubCategory = SubCategory.builder()
            .categoryId(addCategorySubCategoryReq.getCategoryId())
            .title(addCategorySubCategoryReq.getSubtitle())
            .build();

        subCategoryRepository.save(saveSubCategory);
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

