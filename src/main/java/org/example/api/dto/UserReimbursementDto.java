package org.example.api.dto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserReimbursementDto {

    private List<ReceiptDto> receipts;
    private int carMillage;
    private LocalDate startDay;
    private LocalDate endDay;

    public UserReimbursementDto(List<ReceiptDto> receipts, int carMillage, LocalDate startDay, LocalDate endDay) {
        this.receipts = receipts;
        this.carMillage = carMillage;
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public UserReimbursementDto() {

    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    public void setEndDay(LocalDate endDay) {
        this.endDay = endDay;
    }

    public List<ReceiptDto> getReceipts() {
        return Optional.ofNullable(receipts).orElse(Collections.emptyList());
    }

    public void setReceipts(List<ReceiptDto> receipts) {
        this.receipts = Optional.ofNullable(receipts).orElse(Collections.emptyList());
    }

    public int getCarMillage() {
        return carMillage;
    }

    public void setCarMillage(int carMillage) {
        this.carMillage = carMillage;
    }

}