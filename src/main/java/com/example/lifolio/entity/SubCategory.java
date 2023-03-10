package com.example.lifolio.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="SubCategory")
public class SubCategory implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="category_id")
    private Long categoryId;

    @Column(name="title")
    private String title;

    public void updateSubCategory(Long categoryId, String title) {
        this.categoryId = categoryId;
        this.title = title;
    }
}
