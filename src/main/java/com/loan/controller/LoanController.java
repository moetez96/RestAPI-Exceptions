package com.loan.controller;

import com.loan.entity.Loan;
import com.loan.exception.LoanBusinessException;
import com.loan.exception.LoanOwnerException;
import com.loan.request.SubmitLoanRequest;
import com.loan.response.SubmitLoanResponse;
import com.loan.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;

@RestController
public class LoanController {

    private static final String HTTP_HEADER_PARTNER_SECRET = "partner-secret";

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping(value = "/api/loan", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SubmitLoanResponse> submitLoan(@RequestBody SubmitLoanRequest loanRequest) {
        if (loanRequest.getPrincipalAmount() < 100 || loanRequest.getPrincipalAmount() > 99999) {
            throw new LoanBusinessException(
                    "Loan principle amount must be between 100-99999, currently: " +
                            loanRequest.getPrincipalAmount());
        }

        var age = Period.between(loanRequest.getCustomer().getBirthDate(), LocalDate.now()).getYears();
        if (age < 18 || age > 70) {
            throw new LoanBusinessException(
                    "Customer age must be between 18 and 70, currently: " +
                            age);
        }

        var savedLoan = this.loanService.saveLoan(loanRequest, HTTP_HEADER_PARTNER_SECRET);
        var submitLoanResponse = new SubmitLoanResponse();

        submitLoanResponse.setLoanId(savedLoan.getLoanId());
        submitLoanResponse.setCustomerName(savedLoan.getCustomerName());
        submitLoanResponse.setStatus(savedLoan.getStatus());

        return ResponseEntity.status(HttpStatus.CREATED).body(submitLoanResponse);
    }

    @GetMapping(value = "/api/loan", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Loan> findLoan(@RequestParam(name = "loan_id") String loanId) {
        var existingLoan = this.loanService.findLoan(loanId, HTTP_HEADER_PARTNER_SECRET);

        if (existingLoan == null) {
            if (this.loanService.isLoanIdExist(loanId)) {
                throw new LoanOwnerException("You cannot access this loan: " + loanId);
            } else {
                throw new LoanBusinessException("Loan " + loanId + " does not exist");
            }
        }

        return ResponseEntity.ok().body(existingLoan);
    }

}
