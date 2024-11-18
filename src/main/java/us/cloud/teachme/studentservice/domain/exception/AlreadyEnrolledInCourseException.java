package us.cloud.teachme.studentservice.domain.exception;

import org.springframework.http.HttpStatus;
import us.cloud.teachme.studentservice.domain.annotation.HttpStatusMapping;

@HttpStatusMapping(HttpStatus.CONFLICT)
public class AlreadyEnrolledInCourseException extends DomainException {

    public AlreadyEnrolledInCourseException(String courseId) {
        super("Already enrolled in course: " + courseId);
    }
}
