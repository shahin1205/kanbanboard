package com.niit.UserRegistrationService.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Task {
    @Id
    private String taskId;
    private String taskTitle;
    private String priority;
    private String statusCode;
    private String calendar;
    private String projectId; // New field

    public Task() {
    }

    public Task(String taskId, String taskTitle, String priority, String statusCode, String calendar, String projectId) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.priority = priority;
        this.statusCode = statusCode;
        this.calendar = calendar;
        this.projectId = projectId; // Assign projectId
    }

    // Getters and Setters
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", taskTitle='" + taskTitle + '\'' +
                ", priority='" + priority + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", calendar='" + calendar + '\'' +
                ", projectId='" + projectId + '\'' + // Include projectId
                '}';
    }
}
