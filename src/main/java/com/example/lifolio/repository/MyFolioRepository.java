package com.example.lifolio.repository;

import com.example.lifolio.entity.MyFolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
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


    @Query(value="select SC.title as 'category',MFI.url,MF.title,star,color_name'color' " +
            "from MyFolio MF join SubCategory SC on MF.category_id=SC.id " +
            "left join MyFolioImg MFI on MF.id = MFI.folio_id " +
            "join Category C on SC.category_id = C.id " +
            "join Color on Color.id=C.color_id " +
            "where MF.user_id=:userId order by star desc limit 5", nativeQuery = true)
    List<MyFolioRepository.BestCategory> getBestCategories(@Param("userId") Long userId);
    interface BestCategory {
        String getColor();
        String getCategory();
        String getUrl();
        String getTitle();
        int getStar();
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


    int countByUserId(Long userId);

    @Query(value="select MF.date, color_name'color',MF.id'folioId', MFI.url,MF.title\n" +
            "    from MyFolio MF\n" +
            "    left join MyFolioImg MFI on MF.id = MFI.folio_id\n" +
            "    left join SubCategory SC on MF.category_id = SC.id\n" +
            "    left join Category C on SC.category_id = C.id\n" +
            "    left join Color on C.color_id = Color.id\n" +
            "    join (select date,max(star)as max_star from MyFolio group by date)as MF2 on MF2.max_star=MF.star and MF.date=MF2.date\n" +
            "    where MF.user_Id = :userId and DATE_FORMAT(MF.date,'%Y-%m')=:date\n" +
            "    group by date\n" +
            "    order by date asc;",nativeQuery = true)
    List<MyFolioRepository.Calender> getCalenderList(@Param("userId")Long userId,@Param("date") String date);
    public interface Calender {
        LocalDate getDate();
        String getColor();
        Long getFolioId();
        String getUrl();
        String getTitle();
    }


    List<MyFolio> findAllByUserIdAndDate(Long userId, Date date);



}