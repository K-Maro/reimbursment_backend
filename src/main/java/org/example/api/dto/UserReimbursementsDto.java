package org.example.api.dto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserReimbursementsDto {

    private List<UserReimbursementDto> userReimbursements;

    public UserReimbursementsDto(List<UserReimbursementDto> userReimbursements) {
        this.userReimbursements = userReimbursements;
    }

    public UserReimbursementsDto() {

    }

    public List<UserReimbursementDto> getUserReimbursements() {
        return Optional.ofNullable(userReimbursements).orElse(Collections.emptyList());
    }

    public void setUserReimbursements(List<UserReimbursementDto> userReimbursements) {
        this.userReimbursements = Optional.ofNullable(userReimbursements).orElse(Collections.emptyList());
    }

}
