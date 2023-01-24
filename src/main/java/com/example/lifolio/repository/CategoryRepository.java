package com.example.lifolio.repository;

import com.example.lifolio.entity.Category;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Transactional
    @Query(value="INSERT INTO Category (user_id, color_id, title) VALUES (userId=:userId, colorId=:colorId, title=:title)",nativeQuery = true)
    List<Category> setCategory(@Param("userId") Long userId, @Param("colorId") Long colorId, @Param("title") String title);


    Optional<Category> findById(Long id);

    List<Category> findByUserId(Long userId);

}
