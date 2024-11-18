package us.cloud.teachme.studentservice.domain.exception;

import org.springframework.http.HttpStatus;
import us.cloud.teachme.studentservice.domain.annotation.HttpStatusMapping;

@HttpStatusMapping(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends DomainException {

    public StudentNotFoundException(String studentId) {
        super("Student not found with id: " + studentId);
    }

}
