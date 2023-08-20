package org.example.service;

import org.example.service.dto.UserType;

import java.util.List;

public class PermissionsService {

    private final AuthService authService;

    public PermissionsService(AuthService authService) {
        this.authService = authService;
    }

    public boolean isNotAdmin(String username) {
        return isUserAccessDenied(username, List.of(UserType.ADMIN));
    }

    public boolean isNotUser(String username) {
        return isUserAccessDenied(username, List.of(UserType.USER));
    }

    public boolean isNotAdminAndNotUser(String username) {
        return isUserAccessDenied(username, List.of(UserType.ADMIN, UserType.USER));
    }

    boolean isUserAccessDenied(String username, List<UserType> userTypes) {
        return !authService.isValidUserTypes(username, userTypes);
    }
}
