package com.niit.UserRegistrationService.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class Space {
    @Id
    private String id;
    private String spaceName;
    private List<Project> projects;

    public Space() {
        this.projects = new ArrayList<>();
    }

    public Space(String id, String spaceName, List<Project> projects) {
        this.id = id;
        this.spaceName = spaceName;
        this.projects = projects;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    @Override
    public String toString() {
        return "Space{" +
                "id='" + id + '\'' +
                ", spaceName='" + spaceName + '\'' +
                ", projects=" + projects +
                '}';
    }
}
