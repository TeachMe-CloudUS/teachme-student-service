package us.cloud.teachme.studentservice.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.cloud.teachme.studentservice.application.adapter.*;
import us.cloud.teachme.studentservice.application.command.CompleteCourseCommand;
import us.cloud.teachme.studentservice.application.command.CreateStudentCommand;
import us.cloud.teachme.studentservice.application.command.EnrollStudentCommand;
import us.cloud.teachme.studentservice.application.command.UpdateStudentCommand;
import us.cloud.teachme.studentservice.application.dto.CourseDetailsCollection;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.web.request.CreateStudentRequestDto;
import us.cloud.teachme.studentservice.web.request.UpdateStudentRequestDto;

// 1. Modificar el estudiante
// 2. todos los endpoints que ya tenemos
// 3. Crear un endpoint api/me (para usar con userId)
// 4. Llamar a los otros microservicios

import java.util.List;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
@Tag(name = "Student Management", description = "APIs for managing students of the teachme platform")
public class StudentController {

    private final StudentAdapter studentService;
    private final EnrollmentAdapter enrollmentService;
    private final CompleteCourseAdapter completeCourseService;
    private final CreateStudentAdapter createStudentService;
    private final UpdateStudentAdapter updateStudentService;

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

    @PostMapping("/{studentId}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable String studentId,
                                                    @RequestBody UpdateStudentRequestDto updateStudentRequestDto) {
        StudentDto updatedStudent = updateStudentService.updateStudent(
                new UpdateStudentCommand(
                        studentId,
                        updateStudentRequestDto.getPhoneNumber(),
                        updateStudentRequestDto.getPlan())
        );
        return ResponseEntity.ok(updatedStudent);
    }

    @GetMapping("{studentId}/courses")
    public ResponseEntity<CourseDetailsCollection> getEnrolledCoursesByStudentId(@PathVariable String studentId) {
        var enrolledCourses = studentService.getEnrolledCourses(studentId);
        return ResponseEntity.ok(enrolledCourses);
    }

    // @Todo: GetCompletedCourses

    // @Todo: GetForumPosts

    // @Todo: CompleteCourse

    // @Todo: WriteForumPost
}
