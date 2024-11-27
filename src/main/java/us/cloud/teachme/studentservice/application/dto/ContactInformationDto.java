package us.cloud.teachme.studentservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "Data Transfer Object representing the contact information of a Student")
@NoArgsConstructor
@AllArgsConstructor
public class ContactInformationDto {

    @Schema(description = "Name of the Student", example = "Esdrick")
    private String name;

    @Schema(description = "Surname of the Student", example = "Rebolledo")
    private String surname;

    @Schema(description = "Contact email address of the Student", example = "test@gmail.com")
    private String email;

    @Schema(description = "Contact phone number of the Student", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "Country of the Student", example = "Germany")
    private String country;
}
