package com.example.lifolio.repository;

import com.example.lifolio.entity.MyFolioImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MyFolioImgRepository extends JpaRepository<MyFolioImg, Long> {

    List<MyFolioImg> findAllByFolioId(Long folioId);
}
