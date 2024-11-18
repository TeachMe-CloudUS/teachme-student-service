package us.cloud.teachme.studentservice.domain.exception;

import org.springframework.http.HttpStatus;
import us.cloud.teachme.studentservice.domain.annotation.HttpStatusMapping;

@HttpStatusMapping(HttpStatus.CONFLICT)
public class CourseAlreadyCompletedException extends DomainException {

    public CourseAlreadyCompletedException(String courseId) {
        super("Course already completed: " + courseId);
    }
}
