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
    List<GraphLifolio> getMainFolio(@Param("userId")Long userId, @Param("year")int year);


    interface GraphLifolio{
        int getStar();
        int getMonth();
    }

    @Query(value="select max(star)'star', concat(YEAR(date),'-',MONTH(date))'day'\n" +
            "from  MyFolio\n" +
            "         join CustomLifolio CL on MyFolio.category_id = CL.category_id\n" +
            "         left join MyFolioImg MFI on MyFolio.id = MFI.folio_id\n" +
            "where CL.id = :customId and CL.user_id=:userId\n" +
            "group by day\n" +
            "order by day;",nativeQuery = true)
    List<CustomGraphLifolio> getGraphLifolio(@Param("userId")Long userId, @Param("customId") Long customId);
    interface CustomGraphLifolio {
        int getStar();
        String getDay();
    }

    @Query(value="select MyFolio.id'folioId',MyFolio.title, url\n" +
            "from MyFolio\n" +
            "         join CustomLifolio CL on MyFolio.category_id = CL.category_id\n" +
            "         left join MyFolioImg MFI on MyFolio.id = MFI.folio_id\n" +
            "where CL.id = :customId and MyFolio.user_id=:userId\n" +
            "order by star desc\n" +
            "limit 4;",nativeQuery = true)
    List<CustomInfoLifolio> getCustomLifolio(@Param("userId")Long userId, @Param("customId") Long customId);
    interface CustomInfoLifolio {
        Long getFolioId();
        String getTitle();
        String getUrl();
    }
}