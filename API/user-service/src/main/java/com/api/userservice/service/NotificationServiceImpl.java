package com.api.userservice.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.userservice.DTO.NotificationDTO;
import com.api.userservice.model.Notification;
import com.api.userservice.model.User;
import com.api.userservice.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    private NotificationRepository notificationRepository;

    private String pattern = "dd/MM/yyyy";

    private DateFormat df = new SimpleDateFormat(pattern);

    @Override
    public void addNewNotification(String content, User user) {
        Notification notification = new Notification();
        notification.setContent(content);
        notification.setUser(user);
        Date today = Calendar.getInstance().getTime();
        String date = df.format(today);
        notification.setDate(date);
        notification.setChecked(false);
        notificationRepository.save(notification);
    }

    @Override
    public int getCountNewNotification(User user) {
        return notificationRepository.getCountNewNotification(user);
    }

    @Override
    public List<NotificationDTO> getNotifications(User user) {
        return notificationRepository.getNotifications(user);
    }

    @Override
    public void setCheckedNotifications(List<NotificationDTO> notifications) {
        for (NotificationDTO notificationDTO: notifications) {
            Optional<Notification> notification = notificationRepository.findById(notificationDTO.getId());
            if (notification.isPresent()){
                Notification updateNotification = notification.get();
                updateNotification.setChecked(true);
                notificationRepository.save(updateNotification);
            }
        }
    }

    @Override
    public void clear(User user) {
        notificationRepository.deleteByUser(user);
    }
}
