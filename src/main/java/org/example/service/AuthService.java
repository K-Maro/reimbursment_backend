package org.example.service;

import org.example.api.dto.UserCredentialsDto;
import org.example.service.dto.User;
import org.example.service.dto.UserType;

import java.util.List;

public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(UserCredentialsDto userCredentialsDto) {
        return userRepository.findUserByUsernameAndPassword(userCredentialsDto.getUsername(), userCredentialsDto.getPassword())
                .orElseThrow(UserNotFoundException::new);
    }

    public boolean isValidUserTypes(String username, List<UserType> userTypes) {
        return userRepository.findUserByUsernameAndUserTypeIn(username, userTypes).isPresent();
    }
}
