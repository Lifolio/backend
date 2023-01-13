package com.example.lifolio.repository;

import com.example.lifolio.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    @Query(value="select SC.title'category' from SubCategory SC join Category C on SC.category_id = C.id where user_id=:userId ",nativeQuery = true)
    List<CategoryList> getCategoryList(@Param("userId") Long userId);
    interface CategoryList {
        String getCategory();
    }
}