package com.example.lifolio.repository;

import com.example.lifolio.entity.MyFolioImg;
import com.example.lifolio.entity.MyFolioWith;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MyFolioWithRepository extends JpaRepository<MyFolioWith, Long> {
    List<MyFolioWith> findAllByFolioId(Long folioId);  //userId -> 이 마이폴리오에 같이하는 사람의 인덱스
}
