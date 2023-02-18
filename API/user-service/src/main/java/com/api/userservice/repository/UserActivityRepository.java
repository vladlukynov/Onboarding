package com.api.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.userservice.model.Team;
import com.api.userservice.model.UserActivity;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
}
