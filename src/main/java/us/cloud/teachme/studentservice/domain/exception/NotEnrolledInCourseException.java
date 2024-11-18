package us.cloud.teachme.studentservice.domain.exception;


import org.springframework.http.HttpStatus;
import us.cloud.teachme.studentservice.domain.annotation.HttpStatusMapping;

@HttpStatusMapping(HttpStatus.BAD_REQUEST)
public class NotEnrolledInCourseException extends DomainException {

    public NotEnrolledInCourseException(String courseId) {
        super("Not enrolled in course: " + courseId);
    }
}
