package com.api.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.userservice.DTO.UserActivityDTO;
import com.api.userservice.model.Team;
import com.api.userservice.model.UserActivity;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
    @Query("SELECT new com.api.userservice.DTO.UserActivityDTO(u.id, u.content) FROM UserActivity u " +
            "WHERE u.user.id=:userId ORDER BY u.date DESC")
    List<UserActivityDTO> userActivityListByUserId(Long userId);
}
