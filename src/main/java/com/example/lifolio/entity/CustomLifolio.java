package com.example.lifolio.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CustomLifolio")
public class CustomLifolio implements Serializable {

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

 @Column(name = "created_at")
 private LocalDateTime createdAt;

 @Column(name = "updated_at")
 private LocalDateTime updatedAt;

}
