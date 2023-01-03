package com.example.lifolio.repository;

import com.example.lifolio.entity.MyFolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyFolioRepository extends JpaRepository<MyFolio, Long> {
    @Query(value="select max(star)as 'star', MONTH(date) 'month', content\n" +
            "from MyFolio\n" +
            "where user_id = :userId \n" +
            "  and YEAR(date) = :year \n" +
            "group by MONTH(date);",nativeQuery = true)
    List<MainLifolio> getMainFolio(@Param("userId")Long userId, @Param("year")int year);
    interface MainLifolio{
        int getStar();
        int getMonth();
    }
}