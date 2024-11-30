package us.cloud.teachme.studentservice.web.controller;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import us.cloud.teachme.studentservice.application.adapter.*;
import us.cloud.teachme.studentservice.application.command.CompleteMyCourseCommand;
import us.cloud.teachme.studentservice.application.command.EnrollMeInCourseCommand;
import us.cloud.teachme.studentservice.application.command.UpdateMeCommand;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.web.request.UpdateStudentRequestDto;

@RestController
@RequestMapping("/api/students/me")
@AllArgsConstructor
@Tag(name = "Authenticated Student Management", description = "APIs for managing an authenticated student of the teachme platform")
public class AuthStudentController {

    private final StudentAdapter studentService;
    private final EnrollmentAdapter enrollmentService;
    private final CompleteCourseAdapter completeCourseService;
    private final CreateStudentAdapter createStudentService;
    private final UpdateStudentAdapter updateStudentService;

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
                                              @RequestBody UpdateStudentRequestDto updateStudentRequestDto) {
        updateStudentService.updateStudent(
                new UpdateMeCommand(
                        claims.getSubject(),
                        updateStudentRequestDto.getPhoneNumber(),
                        updateStudentRequestDto.getPlan(),
                        updateStudentRequestDto.getName(),
                        updateStudentRequestDto.getSurname(),
                        updateStudentRequestDto.getEmail(),
                        updateStudentRequestDto.getCountry(),
                        updateStudentRequestDto.getLanguage(),
                        updateStudentRequestDto.getBio()
                )
        );

        return ResponseEntity.noContent().build();
    }

/*
    @GetMapping("/courses")
    public ResponseEntity<CourseDetailsCollection> getEnrolledCourses(@AuthenticationPrincipal Claims claims) {
        var enrolledCourses = studentService.getEnrolledCourses(claims.getSubject());
        return ResponseEntity.ok(enrolledCourses);
    }
*/

    // @Todo: GetCompletedCourses
/*
    @GetMapping("/completed-courses")
    public ResponseEntity<CourseDetailsCollection> getCompletedCourses(@AuthenticationPrincipal Claims claims) {
        var enrolledCourses = studentService.getCompletedCourses(claims.getSubject());
        return ResponseEntity.ok(enrolledCourses);
    }
*/

/*
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
                new CompleteCourseCommand(claims.getSubject(), courseId)
        );

        return ResponseEntity.ok().build();
    }
*/

    // @Todo: GetForumPosts

    // @Todo: WriteForumPost
}
