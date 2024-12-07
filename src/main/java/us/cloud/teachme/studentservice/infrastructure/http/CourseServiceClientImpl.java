package us.cloud.teachme.studentservice.infrastructure.http;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Mono;
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
        try {
            courseServiceClient.get()
                    .uri(String.format("/api/v1/courses/%s", courseId))
                    .retrieve()
                    .onStatus(
                            status -> !status.equals(HttpStatus.OK),
                            (response, request) ->
                                    Mono.error(new RuntimeException("No course found!"))
                    )
                    .toBodilessEntity();

            return true;
        } catch (Exception e) {
            System.err.println("Error validating course: " + e.getMessage());
            return false;
        }
    }

    @Override
    public CourseDetailsCollection getCourseDetails(List<String> courseId) {
        try {
            return courseServiceClient.post()
                    .uri("/api/v1/courses/list")
                    .body(courseId)
                    .retrieve()
                    .toEntity(CourseDetailsCollection.class)
                    .getBody();
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("No course found! Status: " + ex.getStatusCode());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch course details", e);
        }
    }
}
