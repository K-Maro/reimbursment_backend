package org.example.repository;

import org.example.mapper.UserReimbursementMapper;
import org.example.service.UserReimbursementRepository;
import org.example.service.dto.UserReimbursement;

import java.util.*;

public class UserReimbursementRepositoryImpl implements UserReimbursementRepository {

    private final Map<String, List<UserReimbursementProperties>> userReimbursementsMap;
    private final UserReimbursementMapper userReimbursementMapper;

    public UserReimbursementRepositoryImpl(UserReimbursementMapper userReimbursementMapper) {
        this.userReimbursementMapper = userReimbursementMapper;
        this.userReimbursementsMap = new HashMap<>();
    }

    @Override
    public List<UserReimbursement> getUserReimbursementsByUsername(String username) {
        return userReimbursementMapper.mapToUserReimbursements(userReimbursementsMap.getOrDefault(username, Collections.emptyList()));
    }

    @Override
    public List<UserReimbursement> saveUserReimbursement(String username, UserReimbursement userReimbursement) {
        final List<UserReimbursementProperties> userReimbursements = userReimbursementsMap.getOrDefault(username, new ArrayList<>());
        return Optional.ofNullable(userReimbursement)
                .map(userReimbursementMapper::map)
                .map(userReimbursementProperties -> {
                    userReimbursements.add(userReimbursementProperties);
                    return userReimbursementsMap.put(username, userReimbursements);
                })
                .map(userReimbursementMapper::mapToUserReimbursements)
                .orElse(Collections.emptyList());

    }
}
