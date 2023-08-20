package org.example.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReimbursementProperties {

    private final RatesProperties rates;
    private final ReceiptsProperties receipts;
    private final LimitsProperties limits;

    ReimbursementProperties() {
        this.rates = new RatesProperties();
        this.receipts = new ReceiptsProperties();
        this.limits = new LimitsProperties();
    }

    public RatesProperties getRates() {
        return rates;
    }

    public ReceiptsProperties getReceipts() {
        return receipts;
    }

    public List<ReceiptsProperties.Receipt> getReceiptsByNames(List<String> names) {
        return receipts.getReceiptsByNames(names);
    }

    public LimitsProperties getLimits() {
        return limits;
    }

    public static class LimitsProperties {

        private BigDecimal totalReimbursement;
        private int distance;
        private int days;

        LimitsProperties() {
            this.totalReimbursement = new BigDecimal("1000.0");
            this.distance = 100;
            this.days = 10;
        }

        public BigDecimal getTotalReimbursement() {
            return totalReimbursement;
        }

        public void setTotalReimbursement(BigDecimal totalReimbursement) {
            this.totalReimbursement = totalReimbursement;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public LimitsProperties update(int days, int distance, BigDecimal totalReimbursement) {
            this.totalReimbursement = totalReimbursement;
            this.distance = distance;
            this.days = days;
            return this;
        }
    }

    public static class ReceiptsProperties {

        private final List<Receipt> receipts;

        ReceiptsProperties() {
            this.receipts = new ArrayList<>();
            this.receipts.add(new Receipt("TAXI", new BigDecimal("50.0")));
            this.receipts.add(new Receipt("HOTEL", new BigDecimal("200.0")));
            this.receipts.add(new Receipt("PLANE", new BigDecimal("250.0")));
            this.receipts.add(new Receipt("TRAIN", new BigDecimal("150.0")));
        }

        public List<Receipt> getReceipts() {
            return receipts;
        }

        public void addReceipt(String name, BigDecimal limit) {
            receipts.add(new Receipt(name, limit));
        }

        public void deleteReceipt(String name) {
            receipts.removeIf(receipt -> receipt.getName().equalsIgnoreCase(name));
        }

        public List<Receipt> getReceiptsByNames(List<String> names) {
            return receipts.stream()
                    .filter(receipt -> names.contains(receipt.getName()))
                    .collect(Collectors.toList());
        }

        public static class Receipt {

            private final String name;
            private final BigDecimal limit;

            Receipt(String name, BigDecimal limit) {
                this.name = name;
                this.limit = limit;
            }

            public String getName() {
                return name;
            }

            public BigDecimal getLimit() {
                return limit;
            }
        }
    }

    public static class RatesProperties {

        private BigDecimal dailyAllowanceRate;
        private BigDecimal carMileageRate;

        RatesProperties() {
            this.dailyAllowanceRate = new BigDecimal("15.0");
            this.carMileageRate = new BigDecimal("0.3");
        }

        public BigDecimal getDailyAllowanceRate() {
            return dailyAllowanceRate;
        }

        public void setDailyAllowanceRate(BigDecimal dailyAllowanceRate) {
            this.dailyAllowanceRate = dailyAllowanceRate;
        }

        public BigDecimal getCarMileageRate() {
            return carMileageRate;
        }

        public void setCarMileageRate(BigDecimal carMileageRate) {
            this.carMileageRate = carMileageRate;
        }

        public RatesProperties update(BigDecimal carMileageRate, BigDecimal dailyAllowanceRate) {
            this.dailyAllowanceRate = dailyAllowanceRate;
            this.carMileageRate = carMileageRate;
            return this;
        }
    }
}