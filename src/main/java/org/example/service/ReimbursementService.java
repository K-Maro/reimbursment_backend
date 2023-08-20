package org.example.service;

import org.example.service.dto.*;

import java.math.BigDecimal;
import java.util.List;

public class ReimbursementService {

    private final ReimbursementRepository reimbursementRepository;
    private final UserReimbursementRepository userReimbursementRepository;
    private final ReimbursementCalculator reimbursementCalculator;

    public ReimbursementService(ReimbursementRepository reimbursementRepository,
                                UserReimbursementRepository userReimbursementRepository,
                                ReimbursementCalculator reimbursementCalculator) {
        this.reimbursementRepository = reimbursementRepository;
        this.userReimbursementRepository = userReimbursementRepository;
        this.reimbursementCalculator = reimbursementCalculator;
    }

    public ReimbursementRates getRates() {
        return reimbursementRepository.getReimbursementRates();
    }

    public List<ReimbursementReceipt> getReceipts() {
        return reimbursementRepository.getReimbursementReceipts();
    }

    public ReimbursementLimits getLimits() {
        return reimbursementRepository.getReimbursementLimits();
    }

    public ReimbursementRates updateRates(ReimbursementRates rates) {
        return reimbursementRepository.updateRates(rates);
    }

    public ReimbursementLimits updateLimits(ReimbursementLimits limits) {
        return reimbursementRepository.updateLimits(limits);
    }

    public List<ReimbursementReceipt> addReceipt(ReimbursementReceipt receipt) {
        return reimbursementRepository.findReceiptByName(receipt.getName())
                .map(reimbursementReceipt -> reimbursementRepository.getReimbursementReceipts())
                .orElseGet(() -> reimbursementRepository.saveReceipt(receipt));
    }

    public List<ReimbursementReceipt> removeReceipt(String name) {
        return reimbursementRepository.deleteReceipt(name);
    }

    public List<ReimbursementReceipt> updateReceipt(String currentReceiptName, ReimbursementReceipt reimbursementReceipt) {
        return reimbursementRepository.findReceiptByName(currentReceiptName)
                .map(value -> {
                    reimbursementRepository.deleteReceipt(currentReceiptName);
                    return reimbursementRepository.saveReceipt(reimbursementReceipt);
                }).orElseGet(reimbursementRepository::getReimbursementReceipts);
    }

    public List<UserReimbursement> getUserReimbursements(String username) {
        return userReimbursementRepository.getUserReimbursementsByUsername(username);
    }

    public List<UserReimbursement> addUserReimbursement(String username, UserReimbursement userReimbursement) {
        return userReimbursementRepository.saveUserReimbursement(username, userReimbursement);
    }

    public BigDecimal calculate(CalculateReimbursementParams params) {
        return reimbursementCalculator.calculateReimbursement(params);
    }
}