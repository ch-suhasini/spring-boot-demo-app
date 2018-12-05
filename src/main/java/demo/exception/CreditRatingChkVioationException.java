package demo.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CreditRatingChkVioationException extends RuntimeException {
    public CreditRatingChkVioationException(String exception) {
        super(exception);
    }

}

