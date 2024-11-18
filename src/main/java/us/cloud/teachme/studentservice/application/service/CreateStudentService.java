package us.cloud.teachme.studentservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.command.CreateStudentCommand;
import us.cloud.teachme.studentservice.application.port.EventPublisher;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.event.StudentCreatedEvent;
import us.cloud.teachme.studentservice.domain.exception.StudentAlreadyExistsException;
import us.cloud.teachme.studentservice.domain.model.Student;

@Service
@RequiredArgsConstructor
public class CreateStudentService {

    private final StudentRepository studentRepository;

    private final EventPublisher eventPublisher;

    public void createStudent(CreateStudentCommand command) {
        var maybeStudent = studentRepository.findStudentByUserId(command.userId());

        if (maybeStudent.isPresent()) {
            throw new StudentAlreadyExistsException(command.userId());
        }

        Student student = new Student(
                command.userId(),
                command.phoneNumber(),
                command.plan()
        );

        studentRepository.saveStudent(student);

        eventPublisher.publish(
                new StudentCreatedEvent(
                        student.getId(),
                        command.userId()
                )
        );
    }
}
