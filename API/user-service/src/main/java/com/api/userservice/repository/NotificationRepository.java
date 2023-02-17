package com.api.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.api.userservice.DTO.NotificationDTO;
import com.api.userservice.model.Notification;
import com.api.userservice.model.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT count(n.id) FROM Notification n where n.user=:user AND n.checked = false")
    int getCountNewNotification(User user);

    @Query("SELECT new com.api.userservice.DTO.NotificationDTO(n.id, n.content, n.date) FROM Notification n where n.user=:user")
    List<NotificationDTO> getNotifications(User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM Notification n WHERE n.user=:user")
    void deleteByUser(User user);
}
