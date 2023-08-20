package org.example.service;

import org.example.mapper.ReimbursementMapper;
import org.example.repository.ReimbursementRepositoryImpl;
import org.example.service.dto.CalculateReimbursementParams;
import org.example.service.dto.ReimbursementReceipt;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReimbursementCalculatorTest {


    private final ReimbursementRepository reimbursementRepository = new ReimbursementRepositoryImpl(new ReimbursementMapper());

    private final ReimbursementCalculator reimbursementCalculator = new ReimbursementCalculator(reimbursementRepository);

    static Stream<Arguments> calculateReimbursementParams() {
        List<ReimbursementReceipt> receipts1 = Arrays.asList(
                new ReimbursementReceipt("TAXI", BigDecimal.valueOf(15)),
                new ReimbursementReceipt("HOTEL", BigDecimal.valueOf(25))
        );
        List<LocalDate> days1 = Arrays.asList(
                LocalDate.of(2023, 8, 1),
                LocalDate.of(2023, 8, 2)
        );
        CalculateReimbursementParams params1 = new CalculateReimbursementParams(receipts1, 0, days1);
        BigDecimal expected1 = BigDecimal.valueOf(70.0);

        List<ReimbursementReceipt> receipts2 = Arrays.asList(
                new ReimbursementReceipt("PLANE", BigDecimal.valueOf(45)),
                new ReimbursementReceipt("TAXI", BigDecimal.valueOf(10))
        );
        List<LocalDate> days2 = Arrays.asList(LocalDate.of(2023, 8, 10), LocalDate.of(2023, 8, 11));
        CalculateReimbursementParams params2 = new CalculateReimbursementParams(receipts2, 25, days2);
        BigDecimal expected2 = BigDecimal.valueOf(92.5);
        CalculateReimbursementParams params3 = new CalculateReimbursementParams(
                Collections.emptyList(),
                25,
                Collections.emptyList());
        BigDecimal expected3 = BigDecimal.valueOf(7.5);
        List<LocalDate> days4 = Arrays.asList(LocalDate.of(2023, 8, 10), LocalDate.of(2023, 8, 11));
        CalculateReimbursementParams params4 = new CalculateReimbursementParams(
                Collections.emptyList(),
                0,
                days4);
        BigDecimal expected4 = BigDecimal.valueOf(30.0);
        List<ReimbursementReceipt> receipts5 = Arrays.asList(
                new ReimbursementReceipt("PLANE", BigDecimal.valueOf(45)),
                new ReimbursementReceipt("TAXI", BigDecimal.valueOf(10))
        );
        CalculateReimbursementParams params5 = new CalculateReimbursementParams(
                receipts5,
                0,
                Collections.emptyList());
        BigDecimal expected5 = BigDecimal.valueOf(55.0);
        return Stream.of(
                Arguments.of(params1, expected1),
                Arguments.of(params2, expected2),
                Arguments.of(params3, expected3),
                Arguments.of(params4, expected4),
                Arguments.of(params5, expected5)
        );
    }

    @ParameterizedTest
    @MethodSource("calculateReimbursementParams")
    void calculateReimbursement(CalculateReimbursementParams params, BigDecimal expected) {

        //given
        //when
        BigDecimal result = reimbursementCalculator.calculateReimbursement(params);

        //then
        assertEquals(expected, result);
    }
}