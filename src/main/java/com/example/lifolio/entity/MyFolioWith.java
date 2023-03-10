package com.example.lifolio.entity;

import com.example.lifolio.base.BaseEntity;
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
@Table(name = "MyFolioWith")
public class MyFolioWith extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="folio_Id")
    private Long folioId;

    @Column(name="user_name")
    private String userName;

    public MyFolioWith(String userName, MyFolio myFolio) {
        this.userName = userName;
        this.folioId = myFolio.getId();
    }
}
