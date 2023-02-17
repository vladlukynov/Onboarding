package com.api.userservice.service;

import java.util.List;

import com.api.userservice.DTO.NotificationDTO;
import com.api.userservice.model.User;

public interface NotificationService {
    void addNewNotification(String content, User user);

    int getCountNewNotification(User user);

    List<NotificationDTO> getNotifications(User user);

    void setCheckedNotifications(List<NotificationDTO> notifications);

    void clear(User user);
}
