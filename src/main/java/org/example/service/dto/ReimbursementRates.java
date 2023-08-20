package org.example.service.dto;

import java.math.BigDecimal;

public class ReimbursementRates {

    private final BigDecimal dailyAllowanceRate;
    private final BigDecimal carMileageRate;

    public ReimbursementRates(BigDecimal dailyAllowanceRate, BigDecimal carMileageRate) {
        this.dailyAllowanceRate = dailyAllowanceRate;
        this.carMileageRate = carMileageRate;
    }

    public BigDecimal getDailyAllowanceRate() {
        return dailyAllowanceRate;
    }

    public BigDecimal getCarMileageRate() {
        return carMileageRate;
    }


}
