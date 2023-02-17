package com.api.userservice.service;

import com.api.userservice.DTO.EditUserBean;
import com.api.userservice.DTO.RegisterBean;
import com.api.userservice.DTO.UserPageWithoutEmail;
import com.api.userservice.DTO.UserProfile;
import com.api.userservice.model.User;

public interface UserService {
    User registerUser(RegisterBean register);

    boolean isCorrectPassword(User user, String password);

    void changePassword(String password, User user);

    void deleteUser(User user);

    boolean isEmailExist(String email);

    void save(User user);

    void update(User user, EditUserBean editUserBean);

    void updateDate(String email, String date);

    void updateTeam(String email, Long team);

    void updatePost(String email, Long post);

    User findUserByEmail(String email);

    void setLinkPhoto(String linkPhoto, Long userId);

    User getUserById(Long id);

    void deleteAllUsersExceptVerified(String email);

    UserProfile getInfoForProfile(User user);

    UserPageWithoutEmail getInfoForUserPageWithoutEmail(User user);
}
