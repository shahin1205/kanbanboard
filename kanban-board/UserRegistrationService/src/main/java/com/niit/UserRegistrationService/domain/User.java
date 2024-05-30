package com.niit.UserRegistrationService.domain;

import com.niit.UserRegistrationService.Enum.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class User {

    @Id
    private String emailId;
    private String password;
    private String firstName;
    private Role role;
    private List<Space> spaces;

    // Default constructor
    public User() {
        this.spaces = new ArrayList<>();
    }

    // Parameterized constructor
    public User(String emailId, String password, String firstName, Role role, List<Space> spaces) {
        this.emailId = emailId;
        this.password = password;
        this.firstName = firstName;
        this.role = role;
        this.spaces = spaces != null ? spaces : new ArrayList<>();
    }

    public boolean hasMemberRole() {
        return this.role == Role.MEMBER;
    }

    public void addProject(Project project) {
        if (this.role == Role.MEMBER) {
            if (this.spaces == null) {
                this.spaces = new ArrayList<>();
            }
            // Assuming you want to add the project to the first space
            if (!this.spaces.isEmpty()) {
                Space firstSpace = this.spaces.get(0);
                firstSpace.addProject(project);
            } else {
                // Handle case when no space exists
                throw new RuntimeException("User does not have any spaces to add the project to");
            }
        } else {
            throw new RuntimeException("Only members can have projects assigned to them");
        }
    }

    // Getters and Setters
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Space> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<Space> spaces) {
        this.spaces = spaces;
    }

    public void addSpace(Space space) {
        if (this.spaces == null) {
            this.spaces = new ArrayList<>();
        }
        this.spaces.add(space);
    }

    public void addProjectToSpace(String spaceId, Project project) {
        for (Space space : this.spaces) {
            if (space.getId().equals(spaceId)) {
                space.getProjects().add(project);
                return;
            }
        }
        throw new RuntimeException("Space not found");
    }

    @Override
    public String toString() {
        return "User{" +
                "emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", role=" + role +
                ", spaces=" + spaces +
                '}';
    }
}
