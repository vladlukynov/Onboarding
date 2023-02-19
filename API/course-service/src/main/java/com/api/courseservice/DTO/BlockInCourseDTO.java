package com.api.courseservice.DTO;

import java.util.List;
import java.util.Set;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.api.courseservice.model.Feedback;
import com.api.courseservice.model.Test;
import com.api.courseservice.model.TextMaterials;

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
