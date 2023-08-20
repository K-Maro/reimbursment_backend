package org.example.service.dto;

import java.time.LocalDate;
import java.util.List;

public class CalculateReimbursementParams {

    private final List<ReimbursementReceipt> receipts;
    private final int carMillage;
    private final List<LocalDate> days;

    public CalculateReimbursementParams(List<ReimbursementReceipt> receipts, int carMillage, List<LocalDate> days) {
        this.receipts = receipts;
        this.carMillage = carMillage;
        this.days = days;
    }

    public List<ReimbursementReceipt> getReceipts() {
        return receipts;
    }

    public int getCarMillage() {
        return carMillage;
    }

    public List<LocalDate> getDays() {
        return days;
    }

}
