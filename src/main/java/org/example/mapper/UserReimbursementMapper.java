package org.example.mapper;

import org.example.api.dto.ReceiptDto;
import org.example.api.dto.UserReimbursementDto;
import org.example.api.dto.UserReimbursementsDto;
import org.example.repository.UserReimbursementProperties;
import org.example.service.dto.ReimbursementReceipt;
import org.example.service.dto.UserReimbursement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UserReimbursementMapper {

    public List<UserReimbursement> mapToUserReimbursements(List<UserReimbursementProperties> userReimbursements) {
        return userReimbursements.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private UserReimbursement map(UserReimbursementProperties userReimbursementProperties) {
        return new UserReimbursement(
                mapToReimbursementReceipts(userReimbursementProperties.getReceipts()),
                userReimbursementProperties.getCarMillage(),
                userReimbursementProperties.getDays(),
                userReimbursementProperties.getReimbursementValue()
        );
    }

    public UserReimbursementProperties map(UserReimbursement userReimbursement) {
        return new UserReimbursementProperties(
                mapToReimbursementReceipts(userReimbursement.getReceipts()),
                userReimbursement.getCarMillage(),
                userReimbursement.getDays(),
                userReimbursement.getReimbursementValue()
        );
    }

    private List<ReimbursementReceipt> mapToReimbursementReceipts(List<ReimbursementReceipt> receipts) {
        return receipts.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private ReimbursementReceipt map(ReimbursementReceipt reimbursementReceipt) {
        return new ReimbursementReceipt(reimbursementReceipt.getName(), reimbursementReceipt.getValue());
    }

    public UserReimbursementsDto mapToDto(List<UserReimbursement> userReimbursements) {
        return new UserReimbursementsDto(userReimbursements.stream()
                .map(this::mapToUserReimbursementDto)
                .collect(Collectors.toList()));
    }

    private UserReimbursementDto mapToUserReimbursementDto(UserReimbursement userReimbursement) {
        return new UserReimbursementDto(
                mapToReceiptsDto(userReimbursement.getReceipts()),
                userReimbursement.getCarMillage(),
                userReimbursement.getStartDay(),
                userReimbursement.getEndDay());
    }

    public UserReimbursement map(UserReimbursementDto userReimbursement, BigDecimal calculatedReimbursement) {
        return new UserReimbursement(
                map(userReimbursement.getReceipts()),
                userReimbursement.getCarMillage(),
                getDatesBetween(userReimbursement.getStartDay(), userReimbursement.getEndDay()),
                calculatedReimbursement
        );
    }

    private List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate)
                .collect(Collectors.toList());
    }

    private List<ReimbursementReceipt> map(List<ReceiptDto> receipts) {
        return receipts.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private List<ReceiptDto> mapToReceiptsDto(List<ReimbursementReceipt> receipts) {
        return receipts.stream()
                .map(this::mapToReceiptDto)
                .collect(Collectors.toList());
    }

    private ReimbursementReceipt map(ReceiptDto receiptDto) {
        return new ReimbursementReceipt(receiptDto.getName(), receiptDto.getValue());
    }

    private ReceiptDto mapToReceiptDto(ReimbursementReceipt receipt) {
        return new ReceiptDto(receipt.getName(), receipt.getValue());
    }
}