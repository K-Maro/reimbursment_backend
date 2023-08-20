package org.example.service;

import org.example.service.dto.User;
import org.example.service.dto.UserType;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findUserByUsernameAndPassword(String username, String password);

    Optional<User> findUserByUsernameAndUserTypeIn(String username, List<UserType> userTypes);
}
