package us.cloud.teachme.studentservice.application.port;

import us.cloud.teachme.studentservice.application.dto.CourseDetailsCollection;

import java.util.List;

public interface CourseServiceClient {

    boolean validateCourse(String courseId);

    CourseDetailsCollection getCourseDetails(List<String> courseId);
}
