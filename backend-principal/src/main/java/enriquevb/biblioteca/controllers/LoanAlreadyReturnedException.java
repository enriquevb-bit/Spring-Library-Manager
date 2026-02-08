package enriquevb.biblioteca.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Loan already returned")
public class LoanAlreadyReturnedException extends RuntimeException {

    public LoanAlreadyReturnedException() {
        super();
    }

    public LoanAlreadyReturnedException(String message) {
        super(message);
    }

    public LoanAlreadyReturnedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoanAlreadyReturnedException(Throwable cause) {
        super(cause);
    }

    protected LoanAlreadyReturnedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
