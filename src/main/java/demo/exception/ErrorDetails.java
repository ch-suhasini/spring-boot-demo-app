package demo.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * status: the HTTP status code
 * message: the error message associated with exception
 */
public class ErrorDetails {
    private Date timestamp;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    private HttpStatus status;
    private String message;
    private String details;

    public ErrorDetails(HttpStatus status, Date timestamp, String message, String details) {
        super();
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

}
