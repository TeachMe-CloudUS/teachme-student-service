package us.cloud.teachme.studentservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.cloud.teachme.studentservice.model.Student;
import us.cloud.teachme.studentservice.model.SubscriptionPlan;
import us.cloud.teachme.studentservice.repository.StudentRepository;

@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/test")
    public void test() {
        studentRepository.save(Student.builder()
                .plan(SubscriptionPlan.GOLD)
                .phoneNumber("+4924234")
                .build());
    }
}
