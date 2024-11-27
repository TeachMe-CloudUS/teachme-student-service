package us.cloud.teachme.studentservice.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.cloud.teachme.studentservice.application.adapter.*;

@RestController
@RequestMapping("/api/me")
@AllArgsConstructor
@Tag(name = "Authenticated Student Management", description = "APIs for managing an authenticated student of the teachme platform")
public class AuthStudentController {

    private final StudentAdapter studentService;
    private final EnrollmentAdapter enrollmentService;
    private final CompleteCourseAdapter completeCourseService;
    private final CreateStudentAdapter createStudentService;
    private final UpdateStudentAdapter updateStudentService;

/*
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
                new EnrollStudentCommand(claims.getSubject(), courseId)
        );

        return ResponseEntity.ok().build();
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

/*
    @PostMapping
    public ResponseEntity<StudentDto> updateStudent(@AuthenticationPrincipal Claims claims,
                                                    @RequestBody UpdateStudentRequestDto updateStudentRequestDto) {
        StudentDto updatedStudent = updateStudentService.updateStudent(
                new UpdateStudentCommand(
                        claims.getSubject(),
                        updateStudentRequestDto.getPhoneNumber(),
                        updateStudentRequestDto.getPlan())
        );
        return ResponseEntity.ok(updatedStudent);
    }
*/

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
