package org.example.repository;

import org.example.service.dto.User;

public class UserMapper {

    public User map(UserProperties userProperties) {
        return new User(userProperties.getUsername(), userProperties.getUserType());
    }
}
