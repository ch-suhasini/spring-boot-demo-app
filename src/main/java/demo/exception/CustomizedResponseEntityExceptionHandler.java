package demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR,new Date(), ex.getLocalizedMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * overriding a base method out of the ResponseEntityExceptionHandler and providing our own custom implementation.
     * @param
     * @param request
     * @return Create an error response object and return it with a specific Http Status.
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(CustomerNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND,new Date(), ex.getLocalizedMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(CreditRatingChkVioationException.class)
    public final ResponseEntity<ErrorDetails> handleCreditRatingChkVioationException(CreditRatingChkVioationException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST,new Date(), ex.getLocalizedMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }


}
