package us.cloud.teachme.studentservice.domain.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String message) {
        super(message);
    }

    public StudentNotFoundException() {
        super();
    }
}
