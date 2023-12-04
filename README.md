 # RestAPI-Exceptions

This project demonstrates a simple RESTful API for checking a loan, implemented using Spring Boot with H2 database and comprehensive exception handling.

## Getting Started

Follow these instructions to set up and run the project locally for development and testing purposes.

```
-> git clone https://github.com/moetez96/RestAPI-Exceptions.git
-> cd RestAPI-Exceptions
-> mvn clean install
-> mvn spring-boot:run
```

### Prerequisites
- Java JDK 17
- Maven

## Exception Handling
The application handles the following exceptions:
- ***InvalidInputException***
- ***MethodArgumentNotValidException***
- ***LoanOwnerException***
- ***LoanBusinessException***
- ***HttpMessageNotReadableException***
- ***ArithmeticException***

## API Endpoints
#### Submit Loan
- Method: POST
- URL: /api/loan

```
Request:
{   
    "principalAmount": 20000, 
    "termMonths": 120,
    "collateral":{
        "brand": "Toyota",
        "model": "Camry",
        "manufacturingYear": 2022
    },
    "customer":{
        "name": "Clark Kent",
        "birthDate": "2002-05-24",
        "monthlyIncome": 4500,
        "idNumber": "7482-4889"
    }
}

Response:
{
    "customerName": "Clark Kent",
    "loanId": "7e5a2e6c-0f9b-4e82-92ed-e415bb18b704",
    "status": "PENDING"
}
```

#### Find Loan
- Method: GET
- URL: /api/loan?loan_id=<loan_id>

```
Response:
{
    "loanId": "409558ec-3fbb-4daa-a5ca-6b647edae046",
    "principalAmount": 20000,
    "termMonths": 12,
    "collateralBrand": "Toyota",
    "collateralModel": "Camry",
    "collateralManufacturingYear": 2022,
    "customerName": "Clark Kent",
    "customerBirthDate": "2004-05-24",
    "customerMonthlyIncome": 4500,
    "customerIdNumber": "7482-4889",
    "createdBy": "partner-secret",
    "status": "PENDING",
    "loanInterest": 2400,
    "monthlyInstallment": 1866
}
```
