package org.example.service;

import org.example.service.dto.UserReimbursement;

import java.util.List;

public interface UserReimbursementRepository {

    List<UserReimbursement> getUserReimbursementsByUsername(String username);

    List<UserReimbursement> saveUserReimbursement(String username, UserReimbursement userReimbursement);
}
