package com.example.lifolio.entity;

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
@Table(name = "Badge")
public class Badge implements Serializable {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "id")
 private Long id;

 @Column(name = "url")
 private String url;

 @Column(name = "title")
 private String title;

}
