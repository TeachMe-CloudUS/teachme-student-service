package us.cloud.teachme.studentservice.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.cloud.teachme.studentservice.domain.model.valueObject.SubscriptionPlan;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentRequestDto {

    @NotBlank(message = "User ID is required")
    @Schema(description = "Unique ID of the user", example = "12345")
    private String userId;

    @NotBlank(message = "Name is required")
    @Schema(description = "Name of the Student", example = "Esdrick")
    private String name;

    @NotBlank(message = "Surname is required")
    @Schema(description = "Surname of the Student", example = "Rebolledo")
    private String surname;

    @NotBlank(message = "Email number is required")
    @Email
    @Schema(description = "Contact email address of the Student", example = "test@gmail.com")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number format")
    @Schema(description = "Contact phone number of the Student", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "Country of the Student", example = "Germany")
    private String country;

    @NotNull(message = "Subscriptionplan is required")
    @Schema(description = "Subscription plan for the student", example = "BASIC")
    private SubscriptionPlan plan;

    @NotBlank(message = "Language is required")
    @Schema(description = "Language of the Student", example = "Germany")
    private String language;

    @Schema(description = "Profile description of the Student", example = "Ambitious CS student @US")
    private String bio;
}
