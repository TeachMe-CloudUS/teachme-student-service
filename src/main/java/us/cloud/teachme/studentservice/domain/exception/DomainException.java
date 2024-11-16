package us.cloud.teachme.studentservice.domain.exception;

public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }

    public DomainException() {
        super();
    }
}
