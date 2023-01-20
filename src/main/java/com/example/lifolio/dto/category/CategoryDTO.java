package com.example.lifolio.dto.category;

import com.example.lifolio.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    private Long userId;
    private Long colorId;
    private String title;
    private String branch;
    private String level;
    private String parentCategoryName;
    private Map<String, CategoryDTO> children;

/*
    public CategoryDTO(Category entity) {
        this.categoryId = entity.getId();
        this.branch = entity.getBranch();
        this.userId = entity.getUserId();
        this.colorId = entity.getColorId();
        this.title = entity.getTitle();
        this.level = entity.getLevel();
        if (entity.getParentCategory() == null) {
            this.parentCategoryName = "대분류";
        } else {
            this.parentCategoryName = entity.getParentCategory().getTitle();
        }

        this.children = entity.getSubCategory() == null ? null :
                entity.getSubCategory().stream().collect(Collectors.toMap(
                        Category::getTitle, CategoryDTO::new
                ));
    }
 */

    public Category toEntity() {
        return Category.builder()
                .userId(userId)
                .colorId(colorId)
                .title(title)
                .branch(branch)
                .level(level)
                .build();
    }

}
