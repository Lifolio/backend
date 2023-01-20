package com.example.lifolio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
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

 @Column(name = "branch")
 private String branch;

 @Column(name = "level")
 private String level;


 public void updateCategory(Long userId, Long colorId, String title) {
  this.userId = userId;
  this.colorId = colorId;
  this.title = title;
 }

 /*
 @JsonIgnore
 @ManyToOne (fetch = FetchType.LAZY)
 @JoinColumn (name ="parent_category_id")
 private Category parentCategory;
 //계층형 구조를 위해 자신의 PK를 부모로 삼는 외래키(연관관계 주인)

 @JsonIgnore
 @OneToMany (mappedBy = "parentCategory", cascade = CascadeType.ALL)
 @Builder.Default
 private List<Category> subCategory = new ArrayList<>();

  */

}
