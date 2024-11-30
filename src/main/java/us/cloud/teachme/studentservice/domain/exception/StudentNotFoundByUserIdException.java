package us.cloud.teachme.studentservice.domain.exception;

import org.springframework.http.HttpStatus;
import us.cloud.teachme.studentservice.domain.annotation.HttpStatusMapping;

@HttpStatusMapping(HttpStatus.NOT_FOUND)
public class StudentNotFoundByUserIdException extends DomainException {

    public StudentNotFoundByUserIdException(String userId) {
        super("Student not found with userId: " + userId);
    }

}