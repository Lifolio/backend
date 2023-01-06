package com.example.lifolio.dto.category;

import com.example.lifolio.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private Long userId;
    private Long colorId;
    private String title;

    public CategoryDto(Long id, Long userId, Long colorId, String title) {
        this.id = id;
        this.userId = userId;
        this.colorId = colorId;
        this.title = title;
    }

    public Category toEntity() {
        return Category.builder()
                .id(id)
                .userId(userId)
                .colorId(colorId)
                .title(title)
                .build();
    }
}
