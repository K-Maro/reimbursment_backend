package org.example.repository;

import org.example.service.UserRepository;
import org.example.service.dto.User;
import org.example.service.dto.UserType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private static final Map<String, UserProperties> USERS_MAP = Map.of(
            "admin", UserProperties.getAdmin(),
            "user", UserProperties.getUser()
    );
    private final UserMapper userMapper;

    public UserRepositoryImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findUserByUsernameAndPassword(String username, String password) {
        return Optional.ofNullable(USERS_MAP.get(username.toLowerCase()))
                .filter(userProperties -> userProperties.getPassword().equals(password))
                .map(userMapper::map);
    }

    @Override
    public Optional<User> findUserByUsernameAndUserTypeIn(String username, List<UserType> userTypes) {
        return Optional.ofNullable(USERS_MAP.get(username.toLowerCase()))
                .filter(userProperties -> userTypes.contains(userProperties.getUserType()))
                .map(userMapper::map);
    }
}
