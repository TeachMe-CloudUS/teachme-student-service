package us.cloud.teachme.studentservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.cloud.teachme.studentservice.model.Student;
import us.cloud.teachme.studentservice.repository.StudentRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{id}")
    public Student test(@PathVariable String id) {
        return studentRepository.findById(id).orElse(null);
    }

    // @Todo: Create Student

    // @Todo: Update Student

    // @Todo: Delete Student

    // @Todo: GetEnrolledCourses

    // @Todo: GetCompletedCourses

    // @Todo: GetForumPosts

    // @Todo: EnrollEnCourse

    // @Todo: CompleteCourse

    // @Todo: WriteForumPost
}
