package com.example.lifolio.repository;

import com.example.lifolio.entity.Archive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    @Query(value="select MF.id'folioId', C.color_name'color',SC.title'category' from MyFolio MF " +
            "join SubCategory SC on MF.category_id = SC.id " +
            "join Category on Category.id = SC.category_id " +
            "join Color C on Category.color_id=C.id " +
            "join Archive A on MF.id = A.folio_id where A.user_id=:userId" +
            "",nativeQuery = true)
    List<ArchiveRepository.ArchiveList> getArchiveList(@Param("userId")Long userId);
    interface ArchiveList {
        Long getFolioId();
        String getColor();
        String getCategory();
    }
}