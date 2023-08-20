package org.example.mapper;

import org.example.api.dto.*;
import org.example.repository.ReimbursementProperties;
import org.example.service.dto.CalculateReimbursementParams;
import org.example.service.dto.ReimbursementLimits;
import org.example.service.dto.ReimbursementRates;
import org.example.service.dto.ReimbursementReceipt;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReimbursementMapper {

    public ReimbursementLimits map(ReimbursementProperties.LimitsProperties limits) {
        return new ReimbursementLimits(
                limits.getTotalReimbursement(),
                limits.getDistance(),
                limits.getDays()
        );
    }

    public List<ReimbursementReceipt> map(ReimbursementProperties.ReceiptsProperties receipts) {
        return receipts.getReceipts().stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public ReimbursementReceipt map(ReimbursementProperties.ReceiptsProperties.Receipt receipt) {
        return new ReimbursementReceipt(receipt.getName(), receipt.getLimit());
    }

    public ReimbursementRates map(ReimbursementProperties.RatesProperties rates) {
        return new ReimbursementRates(
                rates.getDailyAllowanceRate(),
                rates.getCarMileageRate()
        );
    }

    public ReimbursementLimitsDto map(ReimbursementLimits limits) {
        return new ReimbursementLimitsDto(
                limits.getTotalReimbursement(),
                limits.getDistance(),
                limits.getDays()
        );
    }

    public ReimbursementReceiptsDto mapToDto(List<ReimbursementReceipt> receipts) {
        return new ReimbursementReceiptsDto(receipts.stream()
                .map(this::map)
                .collect(Collectors.toList()));
    }

    public ReceiptDto map(ReimbursementReceipt receipt) {
        return new ReceiptDto(receipt.getName(), receipt.getValue());
    }

    public ReimbursementRatesDto map(ReimbursementRates rates) {
        return new ReimbursementRatesDto(
                rates.getDailyAllowanceRate(),
                rates.getCarMileageRate()
        );
    }

    public List<ReimbursementReceipt> map(ReimbursementReceiptsDto receipts) {
        return Optional.ofNullable(receipts.getReceipts())
                .orElse(Collections.emptyList()).stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public ReimbursementReceipt map(ReceiptDto receipt) {
        return new ReimbursementReceipt(receipt.getName(), receipt.getValue());
    }

    public ReimbursementLimits map(ReimbursementLimitsDto limits) {
        return new ReimbursementLimits(
                limits.getTotalReimbursement(),
                limits.getDistance(),
                limits.getDays()
        );
    }

    public ReimbursementRates map(ReimbursementRatesDto rates) {
        return new ReimbursementRates(
                rates.getDailyAllowanceRate(),
                rates.getCarMileageRate()
        );
    }

    public List<ReimbursementReceipt> map(List<ReceiptDto> receipts) {
        return receipts.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<ReimbursementReceipt> mapReceipts(List<ReimbursementProperties.ReceiptsProperties.Receipt> receipts) {
        return receipts.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public ReimbursementReceipt mapToDto(ReceiptDto dto) {
        return new ReimbursementReceipt(dto.getName(), dto.getValue());
    }

    public ReimbursementReceiptsDto mapToReceiptsDto(List<ReimbursementReceipt> reimbursementReceipts) {
        return new ReimbursementReceiptsDto(
                reimbursementReceipts.stream()
                        .map(this::map)
                        .collect(Collectors.toList())
        );
    }

    public CalculateReimbursementParams map(UserReimbursementDto dto) {
        return new CalculateReimbursementParams(
                map(dto.getReceipts()),
                dto.getCarMillage(),
                getDatesBetween(dto.getStartDay(), dto.getEndDay())
        );
    }

    private List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate)
                .collect(Collectors.toList());
    }
}