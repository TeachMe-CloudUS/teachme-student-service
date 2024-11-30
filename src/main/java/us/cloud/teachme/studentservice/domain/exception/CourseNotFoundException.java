package us.cloud.teachme.studentservice.domain.exception;

import org.springframework.http.HttpStatus;
import us.cloud.teachme.studentservice.domain.annotation.HttpStatusMapping;

@HttpStatusMapping(HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends DomainException {

    public CourseNotFoundException(String courseId) {
        super("Course not found with id: " + courseId);
    }

}
