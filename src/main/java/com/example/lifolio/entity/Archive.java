
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
@Table(name = "Archive")
public class Archive implements Serializable {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "id")
 private Long id;

 @Column(name = "user_id")
 private Long userId;

 @Column(name = "folio_id")
 private Long folioId;

 @Column(name = "star")
 private Integer star;

}
