package us.cloud.teachme.studentservice.infrastructure.http;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import us.cloud.teachme.studentservice.application.dto.CourseDetailsCollection;
import us.cloud.teachme.studentservice.application.port.CourseServiceClient;

import java.util.List;

@Service
public class CourseServiceClientImpl implements CourseServiceClient {

    private final RestClient courseServiceClient;

    public CourseServiceClientImpl(RestClient courseServiceClient) {
        this.courseServiceClient = courseServiceClient;
    }

    @Override
    public boolean validateCourse(String courseId) {
        return courseServiceClient.get().uri(String.format("/api/courses/%s", courseId))
                .exchange((request, response) -> {
                    if (response.getStatusCode() != HttpStatus.OK) {
                        throw new RuntimeException("No course found!");
                    }
                    return true;
                });
    }

    @Override
    public CourseDetailsCollection getCourseDetails(List<String> courseId) {
        return courseServiceClient.get().uri("/api/courses")
                .exchange((request, response) -> {
                    if (response.getStatusCode() != HttpStatus.OK) {
                        throw new RuntimeException(response.getStatusText());
                    }
                    return response.bodyTo(CourseDetailsCollection.class);

                });
    }
}
