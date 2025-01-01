package us.cloud.teachme.studentservice.web.controller;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import us.cloud.teachme.studentservice.application.adapter.*;
import us.cloud.teachme.studentservice.application.command.*;
import us.cloud.teachme.studentservice.application.dto.CourseDetailsCollection;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.web.request.UpdateStudentRequestDto;

@RestController
@RequestMapping("/api/v1/students/me")
@AllArgsConstructor
@SecurityRequirement(name = "Authorization")
@Tag(name = "Authenticated Student Management", description = "APIs for managing an authenticated student of the teachme platform")
public class AuthStudentController {

    private final StudentAdapter studentService;
    private final EnrollmentAdapter enrollmentService;
    private final CompleteCourseAdapter completeCourseService;
    private final CreateStudentAdapter createStudentService;
    private final GetCoursesAdapter getCoursesService;
    private final UpdateStudentAdapter updateStudentService;
    private final ProfilePictureAdapter profilePictureService;

    @Operation(summary = "Get student by UserId", description = "Retrieve a specific student by their UserId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved student"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping
    public ResponseEntity<StudentDto> getStudent(@AuthenticationPrincipal Claims claims) {
        var student = studentService.getStudentByUserId(claims.getSubject());
        return ResponseEntity.ok(student);
    }

    @Operation(summary = "Enroll student in a course", description = "Enroll a student in a specified course")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully enrolled student in the course"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @PostMapping("/courses/{courseId}/enroll")
    public ResponseEntity<Void> enrollInCourse(
            @AuthenticationPrincipal Claims claims,
            @Parameter(description = "ID of the course for enrollment")
            @PathVariable String courseId) {
        enrollmentService.enrollStudentInCourse(
                new EnrollMeInCourseCommand(claims.getSubject(), courseId)
        );

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Complete a course for a student", description = "Mark a course as completed for a student")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Course marked as completed for the student"),
            @ApiResponse(responseCode = "404", description = "Student not found or course not enrolled")
    })
    @PostMapping("/courses/{courseId}/complete")
    public ResponseEntity<Void> completeCourse(
            @AuthenticationPrincipal Claims claims,
            @Parameter(description = "ID of the course to complete")
            @PathVariable String courseId) {
        completeCourseService.completeStudentCourse(
                new CompleteMyCourseCommand(claims.getSubject(), courseId)
        );

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update student", description = "Update student with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Updated created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Student could not be updated")
    })
    @PutMapping
    public ResponseEntity<Void> updateStudent(@AuthenticationPrincipal Claims claims,
                                              @RequestBody @Valid UpdateStudentRequestDto updateStudentRequestDto) {
        updateStudentService.updateStudent(
                new UpdateMeCommand(
                        claims.getSubject(),
                        updateStudentRequestDto.getPhoneNumber(),
                        updateStudentRequestDto.getPlan(),
                        updateStudentRequestDto.getName(),
                        updateStudentRequestDto.getSurname(),
                        updateStudentRequestDto.getCountry(),
                        updateStudentRequestDto.getLanguage(),
                        updateStudentRequestDto.getBio()
                )
        );

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all student courses", description = "Retrieve a list of all courses a student is enrolled in")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of courses")
    @GetMapping("/courses")
    public ResponseEntity<CourseDetailsCollection> getEnrolledCourses(@AuthenticationPrincipal Claims claims) {
        var enrolledCourses = getCoursesService.getEnrolledCoursesByUserId(claims.getSubject());
        return ResponseEntity.ok(enrolledCourses);
    }

    @Operation(summary = "Get all student´s completed courses", description = "Retrieve a list of all courses a student has completed")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of completed courses")
    @GetMapping("/completed-courses")
    public ResponseEntity<CourseDetailsCollection> getCompletedCourses(@AuthenticationPrincipal Claims claims) {
        var enrolledCourses = getCoursesService.getCompletedCoursesByUserId(claims.getSubject());
        return ResponseEntity.ok(enrolledCourses);
    }

    @Operation(
            summary = "Upload Profile Picture",
            description = "Allows an authenticated user to upload a new profile picture."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile picture uploaded successfully, returns the URL of the uploaded image."),
            @ApiResponse(responseCode = "400", description = "Invalid file or upload request."),
            @ApiResponse(responseCode = "401", description = "Unauthorized access."),
            @ApiResponse(responseCode = "500", description = "Server error occurred during upload.")
    })
    @PostMapping("/profile-picture/upload")
    public ResponseEntity<String> uploadProfilePicture(
            @AuthenticationPrincipal Claims claims,
            @RequestParam("file") MultipartFile file) {

        String fileUrl = profilePictureService.upload(
                new UploadProfilePictureCommand(claims.getSubject(), file)
        );

        return ResponseEntity.ok(fileUrl);
    }

    @Operation(
            summary = "Delete Profile Picture",
            description = "Allows an authenticated user to delete their existing profile picture."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profile picture deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized access."),
            @ApiResponse(responseCode = "404", description = "Profile picture not found."),
            @ApiResponse(responseCode = "500", description = "Server error occurred during deletion.")
    })
    @DeleteMapping("/profile-picture/delete")
    public ResponseEntity<Void> deleteProfilePicture(@AuthenticationPrincipal Claims claims) {
        profilePictureService.delete(
                new DeleteProfilePictureCommand(claims.getSubject())
        );

        return ResponseEntity.noContent().build();
    }

    // @Todo: GetForumPosts

    // @Todo: WriteForumPost
}
