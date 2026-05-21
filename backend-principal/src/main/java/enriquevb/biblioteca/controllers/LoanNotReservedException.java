package enriquevb.biblioteca.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Loan is not in RESERVED state")
public class LoanNotReservedException extends RuntimeException {

    public LoanNotReservedException() {
        super();
    }

    public LoanNotReservedException(String message) {
        super(message);
    }
}
