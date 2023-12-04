package com.loan.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class SubmitLoanRequest {

    private int principalAmount;
    private int termMonths;
    private Collateral collateral;
    private Customer customer;

    public int getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(int principalAmount) {
        this.principalAmount = principalAmount;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(int termMonths) {
        this.termMonths = termMonths;
    }

    public Collateral getCollateral() {
        return collateral;
    }

    public void setCollateral(Collateral collateral) {
        this.collateral = collateral;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public class Collateral {

        private String brand;
        private String model;
        private int manufacturingYear;

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public int getManufacturingYear() {
            return manufacturingYear;
        }

        public void setManufacturingYear(int manufacturingYear) {
            this.manufacturingYear = manufacturingYear;
        }
    }

    public class Customer {

        private String name;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;
        private int monthlyIncome;
        private String idNumber;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
        }

        public int getMonthlyIncome() {
            return monthlyIncome;
        }

        public void setMonthlyIncome(int monthlyIncome) {
            this.monthlyIncome = monthlyIncome;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }
    }

}
