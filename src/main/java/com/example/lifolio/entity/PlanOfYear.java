package com.example.lifolio.entity;

import com.example.lifolio.base.BaseEntity;
import com.example.lifolio.dto.planOfYear.PlanOfYearReq;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PlanOfYear")
public class PlanOfYear extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="title")
    private String title;

    @Column(name="plan_year")
    private String planYear;

    @Column(name="plan_month")
    private String planMonth;

    public void updatePlanOfYear(PlanOfYearReq.UpdatePlanOfYearReq updatePlanOfYearReq) {
        this.title=updatePlanOfYearReq.getTitle();
        this.planYear=updatePlanOfYearReq.getPlanYear();
        this.planMonth=updatePlanOfYearReq.getPlanMonth();
    }
}