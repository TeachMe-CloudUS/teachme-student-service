package us.cloud.teachme.studentservice.infrastructure.http;

import us.cloud.teachme.studentservice.application.port.CourseServiceClient;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;

public class CourseServiceClientImpl implements CourseServiceClient {

    @Override
    public HttpResponse<Void> validateCourse(String courseId) {
        return null;
    }
}
