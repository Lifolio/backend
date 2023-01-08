package com.example.lifolio.repository;

import com.example.lifolio.entity.CustomLifolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomLifolioRepository extends JpaRepository<CustomLifolio, Long> {


    @Query(value="select id'customId',concept,emoji,title'customName' from CustomLifolio where user_id = :userId",nativeQuery = true)
    List<CustomUserLifolio> getCustomFolio(@Param("userId")Long userId);
    interface CustomUserLifolio{
        Long getCustomId();
        int getConcept();
        String getEmoji();
        String getCustomName();
    }

}