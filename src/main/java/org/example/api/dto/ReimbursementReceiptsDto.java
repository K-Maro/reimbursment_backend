package org.example.api.dto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ReimbursementReceiptsDto {

    private List<ReceiptDto> receipts;

    public ReimbursementReceiptsDto(List<ReceiptDto> receipts) {
        this.receipts = receipts;
    }

    public ReimbursementReceiptsDto() {
    }

    public List<ReceiptDto> getReceipts() {
        return Optional.ofNullable(receipts).orElse(Collections.emptyList());
    }

    public void setReceipts(List<ReceiptDto> receipts) {
        this.receipts = Optional.ofNullable(receipts).orElse(Collections.emptyList());
    }
}
