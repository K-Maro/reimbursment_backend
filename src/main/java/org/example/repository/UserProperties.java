package org.example.repository;

import org.example.service.dto.UserType;
import org.example.utils.HashUtils;

class UserProperties {

    private final String username;
    private final String password;
    private final UserType userType;

    UserProperties(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    static UserProperties getAdmin() {
        return new UserProperties("Admin", HashUtils.hashPassword("@Admin"), UserType.ADMIN);
    }

    static UserProperties getUser() {
        return new UserProperties("User", HashUtils.hashPassword("@User"), UserType.USER);
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    UserType getUserType() {
        return userType;
    }
}
