package us.cloud.teachme.studentservice.application.port;

import java.net.http.HttpResponse;

public interface CourseServiceClient {

    HttpResponse<Void> validateCourse(String courseId);
}
