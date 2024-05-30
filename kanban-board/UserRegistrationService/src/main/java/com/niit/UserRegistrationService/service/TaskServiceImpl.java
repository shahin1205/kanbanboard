package com.niit.UserRegistrationService.service;

import com.niit.UserRegistrationService.Enum.Role;
import com.niit.UserRegistrationService.domain.Project;
import com.niit.UserRegistrationService.domain.Task;
import com.niit.UserRegistrationService.domain.User;
import com.niit.UserRegistrationService.exception.ProjectNotFoundException;
import com.niit.UserRegistrationService.exception.TaskNotFoundException;
import com.niit.UserRegistrationService.exception.UserNotFoundException;
import com.niit.UserRegistrationService.repository.ProjectRepository;
import com.niit.UserRegistrationService.repository.TaskRepository;
import com.niit.UserRegistrationService.repository.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserTaskRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, UserTaskRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task createTask(String projectId, Task task, String emailId) throws ProjectNotFoundException, UserNotFoundException {
        // Fetch the user by emailId
        User user = userRepository.findById(emailId).orElseThrow(UserNotFoundException::new);

        // Check if the user has the necessary role to create a task
        if (user.getRole() != Role.TEAM_LEAD) {
            throw new RuntimeException("Only Team Leads can create tasks.");
        }

        // Fetch the project by projectId
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);

        // Set the initial status for the task
        task.setStatusCode("To Do");

        // Set the projectId for the task
        task.setProjectId(projectId);

        // Save the task
        Task createdTask = taskRepository.save(task);

        // Add the task to the project
        project.getTasks().add(createdTask);
        projectRepository.save(project);

        return createdTask;
    }


    @Override
    public Task updateTask(String taskId, Task updatedTask) throws TaskNotFoundException {
        Task existingTask = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        existingTask.setTaskTitle(updatedTask.getTaskTitle());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setStatusCode(updatedTask.getStatusCode());
        existingTask.setCalendar(updatedTask.getCalendar());
        return taskRepository.save(existingTask);
    }

    @Override
    public List<Task> getTasksByProjectId(String projectId) throws ProjectNotFoundException {
        // Fetch the tasks by projectId
        return taskRepository.findByProjectId(projectId);
    }



    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void deleteTask(String taskId) throws TaskNotFoundException {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        taskRepository.delete(task);
    }

    @Override
    public void moveTask(String taskId, String newStatus) throws TaskNotFoundException {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        task.setStatusCode(newStatus);
        taskRepository.save(task);
    }
}
