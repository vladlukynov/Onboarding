package com.api.courseservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long id;

    private String name;

    private String description;

    private String photoSrc;

    private boolean closed;

    private int countThemes;

    private double percentageOfCompletion;

    public CourseDTO(Long id, String name, String description, String photoSrc) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photoSrc = "/course/image?id=" + this.id;
    }
}
