package org.example.service;

import org.example.service.dto.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReimbursementCalculator {

    private final ReimbursementRepository reimbursementRepository;

    public ReimbursementCalculator(ReimbursementRepository reimbursementRepository) {
        this.reimbursementRepository = reimbursementRepository;
    }

    public BigDecimal calculateReimbursement(CalculateReimbursementParams params) {

        final List<String> receiptsNames = params.getReceipts().stream()
                .map(ReimbursementReceipt::getName)
                .collect(Collectors.toList());

        final ReimbursementLimits reimbursementLimits = reimbursementRepository.getReimbursementLimits();
        final ReimbursementRates reimbursementRates = reimbursementRepository.getReimbursementRates();
        final Map<String, ReimbursementReceipt> receiptPropertiesMap = reimbursementRepository.getReimbursementReceiptsByNames(receiptsNames)
                .stream()
                .collect(Collectors.toMap(ReimbursementReceipt::getName, Function.identity()));

        return getDaysRefund(params.getDays(), reimbursementLimits.getDays(), reimbursementRates.getDailyAllowanceRate())
                .add(getCarMillageRefund(params.getCarMillage(), reimbursementLimits.getDistance(), reimbursementRates.getCarMileageRate()))
                .add(getReceiptsRefund(params.getReceipts(), receiptPropertiesMap))
                .min(reimbursementLimits.getTotalReimbursement());
    }

    private BigDecimal getDaysRefund(List<LocalDate> days, int limitDaysCount, BigDecimal dailyAllowanceRate) {
        return dailyAllowanceRate.multiply(new BigDecimal(Math.min(days.size(), limitDaysCount)));
    }

    private BigDecimal getCarMillageRefund(int carMillage, int distanceLimit, BigDecimal carMileageRate) {
        return carMileageRate.multiply(new BigDecimal(Math.min(carMillage, distanceLimit)));
    }

    private BigDecimal getReceiptsRefund(List<ReimbursementReceipt> userReceipts, Map<String, ReimbursementReceipt> receiptsProperties) {
        return userReceipts.stream()
                .map(userReceipt -> userReceipt.getValue().min(receiptsProperties.get(userReceipt.getName()).getValue()))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
