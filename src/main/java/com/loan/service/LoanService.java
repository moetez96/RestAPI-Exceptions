package com.loan.service;

import com.loan.entity.Loan;
import com.loan.repository.LoanRepository;
import com.loan.request.SubmitLoanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan saveLoan(SubmitLoanRequest loanRequest, String partnerSecret) {
        var loanInterest =
                (int) Math.ceil(loanRequest.getPrincipalAmount() * loanRequest.getTermMonths() * 0.01);
        var monthlyInstallment =
                (int) Math.ceil((loanRequest.getPrincipalAmount() + loanInterest) / loanRequest.getTermMonths());

        var loanEntity = map(loanRequest);

        loanEntity.setLoanInterest(loanInterest);
        loanEntity.setMonthlyInstallment(monthlyInstallment);
        loanEntity.setCreatedBy(partnerSecret);
        loanEntity.setStatus("PENDING");

        return this.loanRepository.save(loanEntity);
    }

    public Loan findLoan(String loanId, String partnerSecret) {
        return this.loanRepository.findByLoanIdAndCreatedBy(UUID.fromString(loanId), partnerSecret)
                .orElse(null);
    }

    public boolean isLoanIdExist(String loanId) {
        return this.loanRepository.existsById(UUID.fromString(loanId));
    }

    private Loan map(SubmitLoanRequest original) {
        var result = new Loan();

        result.setPrincipalAmount(original.getPrincipalAmount());
        result.setTermMonths(original.getTermMonths());

        result.setCollateralBrand(original.getCollateral().getBrand());
        result.setCollateralManufacturingYear(original.getCollateral().getManufacturingYear());
        result.setCollateralModel(original.getCollateral().getModel());

        result.setCustomerBirthDate(original.getCustomer().getBirthDate());
        result.setCustomerIdNumber(original.getCustomer().getIdNumber());
        result.setCustomerMonthlyIncome(original.getCustomer().getMonthlyIncome());
        result.setCustomerName(original.getCustomer().getName());

        return result;
    }

}
