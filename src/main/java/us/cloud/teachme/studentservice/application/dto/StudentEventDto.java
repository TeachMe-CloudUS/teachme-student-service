package us.cloud.teachme.studentservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.cloud.teachme.studentservice.domain.model.Student;

@Data
@Schema(description = "Data Transfer Object for sending events representing a student")
@NoArgsConstructor
public class StudentEventDto {

    @Schema(description = "Unique identifier of the student", example = "12345")
    private String id;

    @Schema(description = "User ID of the student", example = "user123")
    private String userId;

    @Schema(description = "Name of the Student", example = "Esdrick")
    private String name;

    @Schema(description = "Surname of the Student", example = "Rebolledo")
    private String surname;

    @Schema(description = "Contact email address of the Student", example = "test@gmail.com")
    private String email;

    public StudentEventDto(Student student) {
        this.id = student.getId();
        this.userId = student.getUserId().value();
        this.name = student.getContactInformation().getName().name();
        this.surname = student.getContactInformation().getName().surname();
        this.email = student.getContactInformation().getEmail().email();
    }
}
