package us.cloud.teachme.studentservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.cloud.teachme.studentservice.domain.model.Student;
import us.cloud.teachme.studentservice.domain.model.SubscriptionPlan;

import java.util.List;

@Data
@Schema(description = "Data Transfer Object representing a student")
@NoArgsConstructor
public class StudentDto {

    @Schema(description = "Unique identifier of the student", example = "12345")
    private String id;

    @Schema(description = "User ID of the student", example = "user123")
    private String userId;

    @Schema(description = "Phone number of the student", example = "1234567890")
    private String phoneNumber;

    @Schema(description = "Subscription plan of the student", example = "BASIC", allowableValues = {"BASIC", "PREMIUM"})
    private SubscriptionPlan plan;

    @Schema(description = "List of enrolled course IDs", example = "[\"course1\", \"course2\"]")
    private List<String> enrolledCourses;

    @Schema(description = "List of completed course IDs", example = "[\"course3\"]")
    private List<String> completedCourses;

    @Schema(description = "List of forum posts associated with the student", example = "[\"post1\", \"post2\"]")
    private List<String> forumPosts;

    public StudentDto(Student student) {
        this.id = student.getId();
        this.userId = student.getUserId();
        this.phoneNumber = student.getPhoneNumber();
        this.plan = student.getPlan();
        this.enrolledCourses = student.getEnrolledCourses();
        this.completedCourses = student.getCompletedCourses();
        this.forumPosts = student.getForumPosts();
    }
}
