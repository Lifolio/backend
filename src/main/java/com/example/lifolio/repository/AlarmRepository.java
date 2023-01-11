package com.example.lifolio.repository;

import com.example.lifolio.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    Alarm findByUserId(Long userId);


}