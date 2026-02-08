package enriquevb.biblioteca.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Member not Active")
public class MemberNotActiveException extends RuntimeException {

    public MemberNotActiveException() {
        super();
    }

    public MemberNotActiveException(String message) {
        super(message);
    }

    public MemberNotActiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberNotActiveException(Throwable cause) {
        super(cause);
    }

    protected MemberNotActiveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
