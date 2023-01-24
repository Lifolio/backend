package com.example.lifolio.entity;

import com.example.lifolio.base.BaseEntity;
import com.example.lifolio.dto.planning.PlanningReq;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PlanningMonth")
public class PlanningMonth extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="title")
    private String title;

    @CreatedDate
    @Column(name= "date")
    private LocalDateTime date;

    @Column(name = "success")
    private int success;

    public void updateSuccess(int success) {
        this.success=success;
    }
    public void updateInfo(PlanningReq.PostPlanningInfoReq postPlanningReq){
        this.title=postPlanningReq.getTitle();

    }
}
