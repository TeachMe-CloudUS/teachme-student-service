package us.cloud.teachme.studentservice.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import us.cloud.teachme.studentservice.domain.model.SubscriptionPlan;

@Data
@AllArgsConstructor
public class CreateStudentRequestDto {

    @NotBlank(message = "User ID is required")
    @Schema(description = "Unique ID of the user", example = "12345")
    private String userId;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number format")
    @Schema(description = "Contact phone number of the user", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "Subscription plan for the student", example = "BASIC")
    private SubscriptionPlan plan;
}
