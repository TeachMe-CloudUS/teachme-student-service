package us.cloud.teachme.studentservice.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import us.cloud.teachme.studentservice.application.adapter.*;
import us.cloud.teachme.studentservice.application.command.*;
import us.cloud.teachme.studentservice.application.dto.CourseDetailsCollection;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.web.request.CreateStudentRequestDto;
import us.cloud.teachme.studentservice.web.request.UpdateStudentRequestDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@AllArgsConstructor
@SecurityRequirement(name = "Authorization")
@Tag(name = "Student Management", description = "APIs for managing students of the teachme platform")
public class StudentController {

    private final StudentAdapter studentService;
    private final EnrollmentAdapter enrollmentService;
    private final CompleteCourseAdapter completeCourseService;
    private final CreateStudentAdapter createStudentService;
    private final UpdateStudentAdapter updateStudentService;
    private final GetCoursesAdapter getCoursesService;
    private final ProfilePictureAdapter profilePictureService;

    @Operation(summary = "Get all students", description = "Retrieve a list of all registered students")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of students")
    @GetMapping
    public List<StudentDto> getStudents() {
        return studentService.getStudents();
    }

    @Operation(summary = "Get student by ID", description = "Retrieve a specific student by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved student"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/{studentId}")
    public StudentDto getStudentById(@PathVariable String studentId) {
        return studentService.getStudentById(studentId);
    }

    @Operation(summary = "Get student by UserId", description = "Retrieve a specific student by their UserID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved student"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/byUserId/{userId}")
    public StudentDto getStudentByUserId(@PathVariable String userId) {
        return studentService.getStudentByUserId(userId);
    }

    @Operation(summary = "Enroll student in a course", description = "Enroll a student in a specified course")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully enrolled student in the course"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @PostMapping("/{studentId}/courses/{courseId}/enroll")
    public ResponseEntity<Void> enrollStudent(
            @Parameter(description = "ID of the student to enroll")
            @PathVariable String studentId,
            @Parameter(description = "ID of the course for enrollment")
            @PathVariable String courseId) {
        enrollmentService.enrollStudentInCourse(
                new EnrollStudentCommand(studentId, courseId)
        );

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Complete a course for a student", description = "Mark a course as completed for a student")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Course marked as completed for the student"),
            @ApiResponse(responseCode = "404", description = "Student not found or course not enrolled")
    })
    @PostMapping("/{studentId}/courses/{courseId}/complete")
    public ResponseEntity<Void> completeCourse(
            @Parameter(description = "ID of the student completing the course")
            @PathVariable String studentId,
            @Parameter(description = "ID of the course to complete")
            @PathVariable String courseId) {
        completeCourseService.completeStudentCourse(
                new CompleteCourseCommand(studentId, courseId)
        );

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Create a new student", description = "Register a new student with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Student already exists")
    })
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(
            @Parameter(description = "Student creation request data")
            @Valid
            @RequestBody CreateStudentRequestDto createStudentRequestDto) {
        StudentDto createdStudent = createStudentService.createStudent(new CreateStudentCommand(
                createStudentRequestDto.getUserId(),
                createStudentRequestDto.getName(),
                createStudentRequestDto.getSurname(),
                createStudentRequestDto.getEmail(),
                createStudentRequestDto.getPhoneNumber(),
                createStudentRequestDto.getCountry(),
                createStudentRequestDto.getPlan(),
                createStudentRequestDto.getLanguage(),
                createStudentRequestDto.getBio()
        ));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    @Operation(summary = "Delete a student by ID", description = "Delete a specific student by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted student"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable String studentId) {
        studentService.deleteStudentById(studentId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a student", description = "Update a student with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Updated created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Student could not be updated")
    })
    @PutMapping("/{studentId}")
    public ResponseEntity<Void> updateStudent(@PathVariable String studentId,
                                              @RequestBody UpdateStudentRequestDto updateStudentRequestDto) {
        updateStudentService.updateStudent(
                new UpdateStudentCommand(
                        studentId,
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
    @GetMapping("{studentId}/courses")
    public ResponseEntity<CourseDetailsCollection> getEnrolledCourses(@PathVariable String studentId) {
        var enrolledCourses = getCoursesService.getEnrolledCourses(studentId);
        return ResponseEntity.ok(enrolledCourses);
    }

    @Operation(summary = "Get all student´s completed courses", description = "Retrieve a list of all courses a student has completed")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of completed courses")
    @GetMapping("{studentId}/completed-courses")
    public ResponseEntity<CourseDetailsCollection> getCompletedCourses(@PathVariable String studentId) {
        var completedCourses = getCoursesService.getCompletedCourses(studentId);
        return ResponseEntity.ok(completedCourses);
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
    @PostMapping("/{studentId}/profile-picture/upload")
    public ResponseEntity<String> uploadProfilePicture(
            @PathVariable String studentId,
            @RequestParam("file") MultipartFile file) {

        String fileUrl = profilePictureService.upload(
                new UploadProfilePictureCommand(studentId, file)
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
    @DeleteMapping("/{studentId}/profile-picture/delete")
    public ResponseEntity<Void> deleteProfilePicture(@PathVariable String studentId) {
        profilePictureService.delete(
                new DeleteProfilePictureCommand(studentId)
        );

        return ResponseEntity.noContent().build();
    }

    // @Todo: GetCompletedCourses

    // @Todo: GetForumPosts

    // @Todo: CompleteCourse

    // @Todo: WriteForumPost
}
