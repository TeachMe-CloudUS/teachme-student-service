package us.cloud.teachme.studentservice.domain.exception;

import org.springframework.http.HttpStatus;
import us.cloud.teachme.studentservice.domain.annotation.HttpStatusMapping;

@HttpStatusMapping(HttpStatus.CONFLICT)
public class StudentAlreadyExistsException extends DomainException {

    public StudentAlreadyExistsException(String userId) {
        super("Student already exists for user ID: " + userId);
    }

}
