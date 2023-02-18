package com.api.userservice.service;

import java.util.List;

import com.api.userservice.DTO.TaskDTO;
import com.api.userservice.model.User;

public interface TaskService {
    List<TaskDTO> getTasksByUserId(Long userId);

    boolean setCompletedTask(Long userId, Long taskId, boolean completed);

    void addNewTask(User user, String header, String description, String deadline);
}
