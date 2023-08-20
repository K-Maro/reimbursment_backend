package org.example.service.dto;

public class User {

    private final String username;
    private final UserType userType;

    public User(String username, UserType userType) {
        this.username = username;
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getUsername() {
        return username;
    }
}
