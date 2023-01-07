package com.example.lifolio.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Category")
public class Category implements Serializable {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "id")
 private Long id;

 @Column(name = "user_id")
 private Long userId;

 @Column(name = "color_id")
 private Long colorId;

 @Column(name = "title")
 private String title;

 @ManyToOne (fetch = FetchType.LAZY)
 @JoinColumn (name ="parent_cagegory_id")
 private Category parentCategory;
 //계층형 구조를 위해 자신의 PK를 부모로 삼는 외래키(연관관계 주인)

 @OneToMany (mappedBy = "parentCategory", cascade = CascadeType.ALL)
 private List<Category> subCategory = new ArrayList<>();

 private Integer level;

 @Builder
 public Category(Long id, Long userId, Long colorId, String title, Integer level,Category parentCategory) {
  this.id = id;
  this.userId = userId;
  this.colorId = colorId;
  this.title = title;
  this.level = level;
  this.parentCategory = parentCategory;
 }

 public void updateCategory(Long id, Long userId, Long colorId, String title){
  this.id = id;
  this.userId = userId;
  this.colorId = colorId;
  this.title = title;
 }

}
