package us.cloud.teachme.studentservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.command.EnrollStudentCommand;
import us.cloud.teachme.studentservice.application.port.EventPublisher;
import us.cloud.teachme.studentservice.domain.event.StudentEnrollmentEvent;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundException;
import us.cloud.teachme.studentservice.domain.model.Student;
import us.cloud.teachme.studentservice.infrastructure.persistance.MongoStudentRepository;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final MongoStudentRepository studentRepository;

    private final EventPublisher eventPublisher;

    public void enrollStudentInCourse(EnrollStudentCommand command) {
        Student student = studentRepository.findById(command.studentId())
                .orElseThrow(() -> new StudentNotFoundException(command.studentId()));

        student.enrollInCourse(command.courseId());

        eventPublisher.publish(new StudentEnrollmentEvent(
                command.studentId(),
                command.studentId()
        ));
    }
}
