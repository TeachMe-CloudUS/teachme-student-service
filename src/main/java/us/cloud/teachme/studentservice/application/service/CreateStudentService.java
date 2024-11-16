package us.cloud.teachme.studentservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.command.CreateStudentCommand;
import us.cloud.teachme.studentservice.application.port.EventPublisher;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.event.StudentCreatedEvent;
import us.cloud.teachme.studentservice.domain.model.Student;
import us.cloud.teachme.studentservice.domain.model.SubscriptionPlan;
import us.cloud.teachme.studentservice.domain.exception.StudentAlreadyExistsException;

@Service
@RequiredArgsConstructor
public class CreateStudentService {

    private final StudentRepository studentRepository;

    private final EventPublisher eventPublisher;

    public void createStudent(CreateStudentCommand command) {
        Student student = new Student(
                command.userId(),
                command.phoneNumber(),
                SubscriptionPlan.valueOf(command.plan())
        );

        Student persistedStudent = studentRepository.saveStudent(student);

        if (persistedStudent == null) {
            throw new StudentAlreadyExistsException();
        }

        eventPublisher.publish(
                new StudentCreatedEvent(
                        student.getId(),
                        command.userId()
                )
        );
    }
}
