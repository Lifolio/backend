package com.example.lifolio.repository;

import com.example.lifolio.entity.SubCategory;
import org.apache.velocity.tools.generic.ClassTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    @Query(value="select SC.title'category' from SubCategory SC join Category C on SC.category_id = C.id where user_id=:userId ",nativeQuery = true)
    List<CategoryList> getCategoryList(@Param("userId") Long userId);

    @Query(value="select SC.title'category' from SubCategory SC join Category C on SC.category_id = C.id where category_id=:categoryId ",nativeQuery = true)
    List<CategoryList> getSubCategoryList(@Param("categoryId") Long categoryId);


    List<SubCategory> findByCategoryId(Long categoryId);

    Optional<SubCategory> findById(Long id);

    interface CategoryList {
        String getCategory();
    }
}