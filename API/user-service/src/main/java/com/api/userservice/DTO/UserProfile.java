package com.api.userservice.DTO;

import com.api.userservice.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private Long id;
    private String name;
    private String image;
    private String email;
    private String post;
    private String team;
    private String description;

    public UserProfile(User user) {
        this.id = user.getId();
        this.description = user.getDescription();
        this.name = user.getName();
        this.email = user.getEmail();
        this.post = user.getPost() == null ? null : user.getPost().getName();
        this.team = user.getTeam() == null ? null :user.getTeam().getName();
        this.image = "/user/image?id=" + user.getId();
    }
}
