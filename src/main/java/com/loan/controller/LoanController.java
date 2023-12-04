package com.loan.controller;

import com.loan.entity.Loan;
import com.loan.request.SubmitLoanRequest;
import com.loan.response.SubmitLoanResponse;
import com.loan.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoanController {

    private static final String HTTP_HEADER_PARTNER_SECRET = "partner-secret";

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping(value = "/api/loan", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SubmitLoanResponse> submitLoan(@RequestBody SubmitLoanRequest loanRequest,
                                                  @RequestHeader(name = HTTP_HEADER_PARTNER_SECRET)
                                                  String partnerSecret) {
        var savedLoan = this.loanService.saveLoan(loanRequest, partnerSecret);
        var submitLoanResponse = new SubmitLoanResponse();

        submitLoanResponse.setLoanId(savedLoan.getLoanId());
        submitLoanResponse.setCustomerName(savedLoan.getCustomerName());
        submitLoanResponse.setStatus(savedLoan.getStatus());

        return ResponseEntity.status(HttpStatus.CREATED).body(submitLoanResponse);
    }

    @GetMapping(value = "/api/loan", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Loan> findLoan(@RequestParam(name = "loan_id") String loanId,
                                  @RequestHeader(name = HTTP_HEADER_PARTNER_SECRET)
                                  String partnerSecret) {
        var existingLoan = this.loanService.findLoan(loanId, partnerSecret);

        return ResponseEntity.ok().body(existingLoan);
    }

}
