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
public class CoursePage {
    private Long id;

    private String name;

    private String description;

    private int countThemes;

    private List<BlockInCourseDTO> blocks;
}
