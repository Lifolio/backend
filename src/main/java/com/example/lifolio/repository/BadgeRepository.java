package com.example.lifolio.repository;

import com.example.lifolio.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BadgeRepository extends JpaRepository<Badge,Long> {

    @Query(value = "select B.url,B.title,BU.success " +
            "from BadgeUser BU " +
            "left join Badge B on B.id = BU.badge_id " +
            "where BU.user_id=:userId",nativeQuery = true)
    List<BadgeSuccess> getBadgeByUserId(@Param("userId") Long userId);
    interface BadgeSuccess{
        String getUrl();
        String getTitle();
        int getSuccess();
    }

}
