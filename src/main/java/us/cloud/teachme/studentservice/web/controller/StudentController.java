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
import us.cloud.teachme.studentservice.web.request.CompleteCourseRequestDto;
import us.cloud.teachme.studentservice.web.request.EnrollStudentRequestDto;
import us.cloud.teachme.studentservice.web.request.GetStudentByIdRequestDto;

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
    public StudentDto test(@RequestBody GetStudentByIdRequestDto requestDto) {
        return studentService.getStudentById(requestDto.getStudentId());
    }

    @PostMapping("/{studentId}/courses/{courseId}/enroll")
    public ResponseEntity<Void> enrollStudent(@RequestBody EnrollStudentRequestDto requestDto) {
        enrollmentService.enrollStudentInCourse(
                new EnrollStudentCommand(requestDto.getStudentId(), requestDto.getCourseId())
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{studentId}/courses/{courseId}/complete")
    public ResponseEntity<Void> completeCourse(@RequestBody CompleteCourseRequestDto requestDto) {
        completeCourseService.completeStudentCourse(
                new CompleteCourseCommand(requestDto.getStudentId(), requestDto.getCourseId())
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
