package com.example.lifolio.entity;

import com.example.lifolio.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MyFolio")
public class TemporaryMyFolio extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name="category_id")
    private Long categoryId;

    @Column(name ="title")
    private String title;

    @Column(name="emoji")
    private String content;

    @Column(name="star")
    private Integer star;

    @Column(name="date")
    private Date date;

    @Column(name="status")
    private Integer status;

    @Column(name="latitude")
    private java.math.BigDecimal latitude;

    @Column(name="longitude")
    private java.math.BigDecimal longitude;

}