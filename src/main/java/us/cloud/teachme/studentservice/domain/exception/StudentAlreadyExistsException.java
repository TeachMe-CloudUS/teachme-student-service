package us.cloud.teachme.studentservice.domain.exception;

public class StudentAlreadyExistsException extends RuntimeException {

    public StudentAlreadyExistsException(String message) {
        super(message);
    }

    public StudentAlreadyExistsException() {
        super();
    }
}
