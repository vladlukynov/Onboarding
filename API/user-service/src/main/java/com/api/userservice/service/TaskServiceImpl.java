package com.api.userservice.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.userservice.DTO.TaskDTO;
import com.api.userservice.model.Task;
import com.api.userservice.model.User;
import com.api.userservice.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    private String pattern = "MM/dd/yyyy";

    private DateFormat df = new SimpleDateFormat(pattern);

    @Override
    public List<TaskDTO> getTasksByUserId(Long userId) {
        return taskRepository.getTasksByUserId(userId);
    }

    @Override
    public List<TaskDTO> getTasksByUserId(Long userId, boolean completed) {
        return taskRepository.getTasksByUserId(userId, completed);
    }

    @Override
    public boolean setCompletedTask(Long userId, Long taskId, boolean completed) {
        List<TaskDTO> tasks = getTasksByUserId(userId);
        boolean flag = false;
        for (TaskDTO task : tasks) {
            if (task.getId().equals(taskId)) {
                flag = true;
                break;
            }
        }
        if (flag) {
            taskRepository.setCompletedTask(taskId, completed);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addNewTask(User user, String header, String description, String deadline) {
        Task task = new Task();
        task.setUser(user);
        task.setDeadlineDate(deadline);
        task.setHeader(header);
        task.setDescription(description);
        task.setCompleted(false);
        Date today = Calendar.getInstance().getTime();
        String date = df.format(today);
        task.setDateStart(date);
        taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            return taskOptional.get();
        }
        throw new EntityNotFoundException();
    }
}
