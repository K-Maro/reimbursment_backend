package org.example.service;

import org.example.service.dto.ReimbursementLimits;
import org.example.service.dto.ReimbursementRates;
import org.example.service.dto.ReimbursementReceipt;

import java.util.List;
import java.util.Optional;

public interface ReimbursementRepository {

    ReimbursementLimits getReimbursementLimits();

    List<ReimbursementReceipt> getReimbursementReceipts();

    List<ReimbursementReceipt> getReimbursementReceiptsByNames(List<String> names);

    ReimbursementRates getReimbursementRates();

    List<ReimbursementReceipt> saveReceipt(ReimbursementReceipt receipt);

    List<ReimbursementReceipt> deleteReceipt(String name);

    Optional<ReimbursementReceipt> findReceiptByName(String name);

    ReimbursementRates updateRates(ReimbursementRates rates);

    ReimbursementLimits updateLimits(ReimbursementLimits limits);}