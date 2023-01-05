package com.example.lifolio.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


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

 public void updateCategory(Long id, Long userId, Long colorId, String title){
  this.id = id;
  this.userId = userId;
  this.colorId = colorId;
  this.title = title;
 }

}
