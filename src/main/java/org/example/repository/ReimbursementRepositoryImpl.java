package org.example.repository;

import org.example.mapper.ReimbursementMapper;
import org.example.service.ReimbursementRepository;
import org.example.service.dto.ReimbursementLimits;
import org.example.service.dto.ReimbursementRates;
import org.example.service.dto.ReimbursementReceipt;

import java.util.List;
import java.util.Optional;

public class ReimbursementRepositoryImpl implements ReimbursementRepository {

    private final ReimbursementProperties reimbursementProperties;
    private final ReimbursementMapper reimbursementMapper;

    public ReimbursementRepositoryImpl(ReimbursementMapper reimbursementMapper) {
        this.reimbursementMapper = reimbursementMapper;
        this.reimbursementProperties = new ReimbursementProperties();
    }

    @Override
    public ReimbursementLimits getReimbursementLimits() {
        return reimbursementMapper.map(reimbursementProperties.getLimits());
    }

    @Override
    public List<ReimbursementReceipt> getReimbursementReceipts() {
        return reimbursementMapper.map(reimbursementProperties.getReceipts());
    }

    @Override
    public List<ReimbursementReceipt> getReimbursementReceiptsByNames(List<String> names) {
        return reimbursementMapper.mapReceipts(reimbursementProperties.getReceiptsByNames(names));
    }

    @Override
    public ReimbursementRates getReimbursementRates() {
        return reimbursementMapper.map(reimbursementProperties.getRates());
    }

    @Override
    public List<ReimbursementReceipt> saveReceipt(ReimbursementReceipt receipt) {
        reimbursementProperties.getReceipts().addReceipt(receipt.getName(), receipt.getValue());
        return reimbursementMapper.map(reimbursementProperties.getReceipts());
    }

    @Override
    public List<ReimbursementReceipt> deleteReceipt(String name) {
        reimbursementProperties.getReceipts().deleteReceipt(name);
        return reimbursementMapper.map(reimbursementProperties.getReceipts());
    }

    @Override
    public Optional<ReimbursementReceipt> findReceiptByName(String name) {
        return reimbursementProperties.getReceipts().getReceipts().stream()
                .filter(receipt -> receipt.getName().equals(name))
                .map(reimbursementMapper::map)
                .findFirst();
    }

    @Override
    public ReimbursementRates updateRates(ReimbursementRates rates) {
        return reimbursementMapper.map(
                reimbursementProperties.getRates()
                        .update(rates.getCarMileageRate(), rates.getDailyAllowanceRate()));
    }

    @Override
    public ReimbursementLimits updateLimits(ReimbursementLimits limits) {
        return reimbursementMapper.map(
                reimbursementProperties.getLimits()
                        .update(limits.getDays(), limits.getDistance(), limits.getTotalReimbursement()));
    }
}