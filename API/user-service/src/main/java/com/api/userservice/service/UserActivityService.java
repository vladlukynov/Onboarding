package com.api.userservice.service;

import com.api.userservice.model.User;

public interface UserActivityService {
    void addNewUserActivity(String activity, User user);
}
