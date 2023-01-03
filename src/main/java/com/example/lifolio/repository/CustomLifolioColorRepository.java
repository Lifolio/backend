package com.example.lifolio.repository;

import com.example.lifolio.entity.CustomLifolioColor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomLifolioColorRepository extends JpaRepository<CustomLifolioColor, Long> {


    CustomLifolioColor findByUserId(Long userId);
}