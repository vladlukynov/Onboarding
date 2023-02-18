package com.api.userservice.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.userservice.DTO.UserActivityDTO;
import com.api.userservice.model.User;
import com.api.userservice.model.UserActivity;
import com.api.userservice.repository.UserActivityRepository;

@Service
public class UserActivityServiceImpl implements UserActivityService {
    @Autowired
    private UserActivityRepository userActivityRepository;

    @Override
    public void addNewUserActivity(String activity, User user) {
        UserActivity userActivity = new UserActivity();
        userActivity.setUser(user);
        userActivity.setContent(activity);
        Date today = Calendar.getInstance().getTime();
        userActivity.setDate(today);
        userActivityRepository.save(userActivity);
    }

    @Override
    public List<UserActivityDTO> getUserActivityListByUserId(Long userId) {
        return userActivityRepository.userActivityListByUserId(userId);
    }
}
