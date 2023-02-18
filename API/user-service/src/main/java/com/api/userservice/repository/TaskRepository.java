package com.api.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.api.userservice.DTO.TaskDTO;
import com.api.userservice.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT new com.api.userservice.DTO.TaskDTO(t.id, t.dateStart, t.deadlineDate, t.header, t.description, t.completed)" +
            " FROM Task t WHERE t.user.id=:userId ORDER BY t.completed ASC")
    List<TaskDTO> getTasksByUserId(Long userId);

    @Query("SELECT new com.api.userservice.DTO.TaskDTO(t.id, t.dateStart, t.deadlineDate, t.header, t.description, t.completed)" +
            " FROM Task t WHERE t.user.id=:userId and t.completed=:completed")
    List<TaskDTO> getTasksByUserId(Long userId, boolean completed);

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.completed=:completed WHERE t.id=:taskId")
    void setCompletedTask(Long taskId, boolean completed);
}
