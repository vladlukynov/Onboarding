package com.api.userservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ColleagueDTO {
    private Long id;
    private String name;
    private String image;
    private String post;

    public ColleagueDTO(Long id, String name, String post) {
        this.id = id;
        this.name = name;
        this.post = post;
        this.image = "/user/image?id=" + id;
    }
}
