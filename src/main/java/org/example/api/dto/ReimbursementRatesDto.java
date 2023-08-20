package org.example.api.dto;

import java.math.BigDecimal;

public class ReimbursementRatesDto {

    private BigDecimal dailyAllowanceRate;
    private BigDecimal carMileageRate;

    public ReimbursementRatesDto(BigDecimal dailyAllowanceRate, BigDecimal carMileageRate) {
        this.dailyAllowanceRate = dailyAllowanceRate;
        this.carMileageRate = carMileageRate;
    }

    public ReimbursementRatesDto() {
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
}
