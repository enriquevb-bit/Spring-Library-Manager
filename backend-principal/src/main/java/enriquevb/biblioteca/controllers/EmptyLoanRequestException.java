package enriquevb.biblioteca.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Loan request items cannot be empty")
public class EmptyLoanRequestException extends RuntimeException {
    public EmptyLoanRequestException() {
    }

    public EmptyLoanRequestException(String message) {
        super(message);
    }
}
