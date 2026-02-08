package enriquevb.biblioteca.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not enough Book copies")
public class NotEnoughCopiesException extends RuntimeException {
    public NotEnoughCopiesException(String message) {
        super(message);
    }

    public NotEnoughCopiesException() {
        super();
    }

    public NotEnoughCopiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughCopiesException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughCopiesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
