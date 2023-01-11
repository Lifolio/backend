package com.example.lifolio.repository;

import com.example.lifolio.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findById(Long id);

    Optional<Category> findByBranchAndTitle(String branch, String title);

    Boolean existsByBranchAndTitle(String branch, String title);
    //DB 여부 반환




}
