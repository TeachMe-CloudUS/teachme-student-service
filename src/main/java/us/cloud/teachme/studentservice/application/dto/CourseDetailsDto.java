package us.cloud.teachme.studentservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "Data Transfer Object representing the course details")
@NoArgsConstructor
public class CourseDetailsDto {

    @Schema(description = "Id of the course")
    private String id;

    @Schema(description = "Name of the course")
    private String name;

    @Schema(description = "Description of the course")
    private String description;

    @Schema(description = "Category of the course")
    private String category;

    @Schema(description = "Duration of the course")
    private String duration;

    @Schema(description = "Level of the course")
    private String level;

    @Schema(description = "Rating of the course")
    private Double rating;
}
