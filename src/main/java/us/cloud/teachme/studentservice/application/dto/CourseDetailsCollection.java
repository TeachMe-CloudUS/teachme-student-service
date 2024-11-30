package us.cloud.teachme.studentservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;

@Schema(description = "Data Transfer Object representing a collection of CourseDetails")
public class CourseDetailsCollection extends ArrayList<CourseDetailsDto> {
}
