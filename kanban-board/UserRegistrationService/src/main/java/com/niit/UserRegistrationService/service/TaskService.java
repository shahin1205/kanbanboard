package com.niit.UserRegistrationService.service;

import com.niit.UserRegistrationService.domain.Task;
import com.niit.UserRegistrationService.exception.ProjectNotFoundException;
import com.niit.UserRegistrationService.exception.TaskNotFoundException;
import com.niit.UserRegistrationService.exception.UserNotFoundException;

import java.util.List;

public interface TaskService {
    Task createTask(String projectId, Task task, String emailId) throws ProjectNotFoundException, UserNotFoundException;
    Task updateTask(String taskId, Task updatedTask) throws TaskNotFoundException;
    List<Task> getTasksByProjectId(String projectId) throws ProjectNotFoundException;

    List<Task> getAllTasks();
    void deleteTask(String taskId) throws TaskNotFoundException;
    void moveTask(String taskId, String newStatus) throws TaskNotFoundException;
}
