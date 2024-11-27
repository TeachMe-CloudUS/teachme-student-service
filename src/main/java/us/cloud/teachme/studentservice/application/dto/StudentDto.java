package us.cloud.teachme.studentservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.cloud.teachme.studentservice.domain.model.Student;

import java.util.List;

@Data
@Schema(description = "Data Transfer Object representing a student")
@NoArgsConstructor
public class StudentDto {

    @Schema(description = "Unique identifier of the student", example = "12345")
    private String id;

    @Schema(description = "User ID of the student", example = "user123")
    private String userId;

    @Schema(description = "Contact information")
    private ContactInformationDto contactInformation;

    @Schema(description = "Profile information")
    private ProfileInformationDto profileInformation;

    @Schema(description = "List of enrolled course IDs", example = "[\"course1\", \"course2\"]")
    private List<String> enrolledCourses;

    @Schema(description = "List of completed course IDs", example = "[\"course3\"]")
    private List<String> completedCourses;

    @Schema(description = "List of forum posts associated with the student", example = "[\"post1\", \"post2\"]")
    private List<String> forumPosts;

    public StudentDto(Student student) {
        this.id = student.getId();
        this.userId = student.getUserId().value();
        this.contactInformation = new ContactInformationDto(
                student.getContactInformation().getName().name(),
                student.getContactInformation().getName().surname(),
                student.getContactInformation().getEmail().email(),
                student.getContactInformation().getPhoneNumber().value(),
                student.getContactInformation().getCountry()
        );
        this.profileInformation = new ProfileInformationDto(
                student.getProfileInformation().getPlan(),
                student.getProfileInformation().getLanguage().language(),
                student.getProfileInformation().getBio()
        );

        this.enrolledCourses = student.getEnrolledCourses();
        this.completedCourses = student.getCompletedCourses();
        this.forumPosts = student.getForumPosts();
    }
}
