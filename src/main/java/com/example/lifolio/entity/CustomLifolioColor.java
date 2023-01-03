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
@Table(name = "CustomLifolioColor")
public class CustomLifolioColor implements Serializable {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "id")
 private Long id;

 @Column(name = "user_id")
 private Long userId;

 @Column(name = "color_status")
 private Integer colorStatus;

}
