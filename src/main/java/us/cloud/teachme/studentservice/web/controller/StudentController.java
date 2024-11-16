package us.cloud.teachme.studentservice.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.cloud.teachme.studentservice.application.command.CompleteCourseCommand;
import us.cloud.teachme.studentservice.application.command.EnrollStudentCommand;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.application.service.CompleteCourseService;
import us.cloud.teachme.studentservice.application.service.EnrollmentService;
import us.cloud.teachme.studentservice.application.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final EnrollmentService enrollmentService;
    private final CompleteCourseService completeCourseService;

    @GetMapping("/")
    public List<StudentDto> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable String id) {
        return studentService.getStudentById(id);
    }

    @PostMapping("/{studentId}/courses/{courseId}/enroll")
    public ResponseEntity<Void> enrollStudent(@PathVariable String studentId, @PathVariable String courseId) {
        enrollmentService.enrollStudentInCourse(
                new EnrollStudentCommand(studentId, courseId)
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{studentId}/courses/{courseId}/complete")
    public ResponseEntity<Void> completeCourse(@PathVariable String studentId, @PathVariable String courseId) {
        completeCourseService.completeStudentCourse(
                new CompleteCourseCommand(studentId, courseId)
        );

        return ResponseEntity.ok().build();
    }

    // @Todo: Create Student

    // @Todo: Update Student

    // @Todo: Delete Student

    // @Todo: GetEnrolledCourses

    // @Todo: GetCompletedCourses

    // @Todo: GetForumPosts

    // @Todo: CompleteCourse

    // @Todo: WriteForumPost
}
