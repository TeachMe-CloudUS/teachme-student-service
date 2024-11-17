package us.cloud.teachme.studentservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.command.EnrollStudentCommand;
import us.cloud.teachme.studentservice.application.port.EventPublisher;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.event.StudentEnrollmentEvent;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundException;
import us.cloud.teachme.studentservice.domain.model.Student;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final StudentRepository studentRepository;

    private final EventPublisher eventPublisher;

    public void enrollStudentInCourse(EnrollStudentCommand command) {
        Student student = studentRepository.findStudentsById(command.studentId())
                .orElseThrow(() -> new StudentNotFoundException(command.studentId()));

        student.enrollInCourse(command.courseId());

        studentRepository.saveStudent(student);

        eventPublisher.publish(new StudentEnrollmentEvent(
                command.studentId(),
                command.studentId()
        ));
    }
}
