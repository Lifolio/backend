package com.example.lifolio.entity;

import com.example.lifolio.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CustomLifolio")
public class CustomLifolio extends BaseEntity implements Serializable {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "id")
 private Long id;

 @Column(name = "user_id")
 private Long userId;

 @Column(name = "category_id")
 private Long categoryId;

 @Column(name = "concept")
 private int concept;

 @Column(name ="title")
 private String title;

 @Column(name="emoji")
 private String emoji;

public void updateCustomLifolio(String title, Long categoryId, int concept, String emoji){
 this.title = title;
 this.categoryId = categoryId;
 this.concept = concept;
 this.emoji = emoji;
}



}
