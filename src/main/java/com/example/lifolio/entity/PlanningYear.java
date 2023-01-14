package com.example.lifolio.entity;

import com.example.lifolio.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PlanningYear")
public class PlanningYear extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="title")
    private String title;

    @Column(name="date")
    private LocalDate date;

    @Column(name = "success")
    private int success;

    public void updateGoalOfYear(LocalDate date,String title){
        this.date = date;
        this.title = title;
    }

    public void updateGoalOfYearSuccess(int success){
        this.success = success;
    }
}
