package us.cloud.teachme.studentservice.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import us.cloud.teachme.studentservice.domain.model.valueObject.SubscriptionPlan;

@Data
@AllArgsConstructor
public class UpdateStudentRequestDto {

    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number format")
    @Schema(description = "Contact phone number of the user", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "Subscription plan for the student", example = "BASIC")
    private SubscriptionPlan plan;

    @Schema(description = "Name of the Student", example = "Esdrick")
    private String name;

    @Schema(description = "Surname of the Student", example = "Rebolledo")
    private String surname;

    @Email
    @Schema(description = "Contact email address of the Student", example = "test@gmail.com")
    private String email;

    @Schema(description = "Country of the Student", example = "Germany")
    private String country;

    @Schema(description = "Language of the Student", example = "Germany")
    private String language;

    @Schema(description = "Profile description of the Student", example = "Ambitious CS student @US")
    private String bio;
}
