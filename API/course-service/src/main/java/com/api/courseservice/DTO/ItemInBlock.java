package com.api.courseservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemInBlock {
    private Long id;

    private String name;

    private Integer type;

    private Integer condition;

    private Integer numberInBlock;
}
