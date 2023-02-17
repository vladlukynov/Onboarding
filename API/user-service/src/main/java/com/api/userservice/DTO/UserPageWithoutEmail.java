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
public class UserPageWithoutEmail {
    private String name;
    private String image;
    private String description;
    private String post;
    private String team;

    public UserPageWithoutEmail(User user) {
        this.name = user.getName();
        this.description = user.getDescription();
        this.post = user.getPost() == null ? null : user.getPost().getName();
        this.team = user.getTeam() == null ? null :user.getTeam().getName();
        this.image = "/user/image?id=" + user.getId();
    }
}
