package us.cloud.teachme.studentservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.cloud.teachme.studentservice.domain.model.SubscriptionPlan;

@Data
@Schema(description = "Data Transfer Object representing the profile information of a Student")
@NoArgsConstructor
@AllArgsConstructor
public class ProfileInformationDto {

    @Schema(description = "Subscription plan of the student", example = "BASIC", allowableValues = {"BASIC", "GOLD", "PLATINUM"})
    private SubscriptionPlan plan;

    @Schema(description = "Language of the Student", example = "Germany")
    private String language;

    @Schema(description = "Profile description of the Student", example = "Ambitious CS student @US")
    private String bio;
}
