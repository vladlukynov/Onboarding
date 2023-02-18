package com.api.userservice.service;

import java.util.List;

import com.api.userservice.DTO.UserActivityDTO;
import com.api.userservice.model.User;
import com.api.userservice.model.UserActivity;
import com.api.userservice.model.UserToken;

public interface UserActivityService {
    void addNewUserActivity(String activity, User user, String percent);
    List<UserActivityDTO> getUserActivityListByUserId(Long userId);
}
