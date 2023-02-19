package com.api.courseservice.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlockInCourseDTO {
    private Long id;

    private String name;

    private List<ItemInBlock> itemsInBlock;

    private int numberInCourse;
}
