package com.example.lifolio.repository;


import com.example.lifolio.entity.MyFolioWith;
import com.example.lifolio.entity.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long> {
    List<Planning> findByUserIdAndDateBetweenOrderByDateAsc(Long userId, LocalDateTime startDate, LocalDateTime finishDate);
    List<Planning> findByUserId(Long userId);

    @Query(value = "select U.id'userId',U.nickname,fcm_token'fcmToken' \n" +
            "from User U\n" +
            "         join Planning P on U.id = P.user_id\n" +
            "         join Alarm A on P.user_id = A.user_id\n" +
            "where todo_alarm = 1\n" +
            "and DATE(date) = DATE(now()) group by U.id;\n",nativeQuery = true)
    List<UserIdList> getUserIdList();


    @Query(value = "select P.success \n" +
            "from User U\n" +
            "         join Planning P on U.id = P.user_id\n" +
            "         join Alarm A on P.user_id = A.user_id\n" +
            "where todo_alarm = 1\n" +
            "and DATE(date) = DATE(now()) and P.user_id = :userId",nativeQuery = true)
    List<TodoList> getTodoList(@Param("userId") Long userId);
    interface UserIdList {
        Long getUserId();
        String getNickname();
        String getFcmToken();
    }

    interface TodoList {
        int getSuccess();
    }
}
