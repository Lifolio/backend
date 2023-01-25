package com.example.lifolio.repository;

import com.example.lifolio.entity.PlanningWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PlanningWeekRepository extends JpaRepository<PlanningWeek, Long> {
    List<PlanningWeek> findByUserIdAndDateBetweenOrderByDateAsc(Long userId, LocalDateTime startDate, LocalDateTime finishDate);

    @Query(value = "select U.id'userId',U.nickname,fcm_token'fcmToken' \n" +
            "from User U\n" +
            "         join PlanningWeek P on U.id = P.user_id\n" +
            "         join Alarm A on P.user_id = A.user_id\n" +
            "where todo_alarm = 1\n" +
            "and date BETWEEN :startTime AND :finishTime group by U.id;\n",nativeQuery = true)
    List<PlanningWeekRepository.UserIdList> getUserIdList(@Param("startTime") LocalDateTime startTime, @Param("finishTime")LocalDateTime finishTime);


    interface UserIdList {
        Long getUserId();
        String getNickname();
        String getFcmToken();
    }

    @Query(value = "select P.success \n" +
            "from User U\n" +
            "         join PlanningWeek P on U.id = P.user_id\n" +
            "         join Alarm A on P.user_id = A.user_id\n" +
            "where todo_alarm = 1\n" +
            "and date BETWEEN :startTime AND :finishTime and P.user_id = :userId",nativeQuery = true)
    List<TodoList> getTodoList(@Param("startTime") LocalDateTime startTime, @Param("finishTime")LocalDateTime finishTime,@Param("userId") Long userId);
    interface TodoList {
        int getSuccess();
    }
}