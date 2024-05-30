package com.niit.UserRegistrationService.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class Project {
    @Id
    private String id;
    private String projectName;
    private List<Task> tasks;
    private String spaceId;

    public Project() {
        this.tasks = new ArrayList<>();
    }

    public Project(String id, String projectName, List<Task> tasks, String spaceId) {
        this.id = id;
        this.projectName = projectName;
        this.tasks = tasks;
        this.spaceId = spaceId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", projectName='" + projectName + '\'' +
                ", tasks=" + tasks +
                ", spaceId='" + spaceId + '\'' +
                '}';
    }
}
