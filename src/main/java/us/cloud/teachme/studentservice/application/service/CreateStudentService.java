package us.cloud.teachme.studentservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.adapter.CreateStudentAdapter;
import us.cloud.teachme.studentservice.application.command.CreateStudentCommand;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.application.port.EventPublisher;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.event.StudentCreatedEvent;
import us.cloud.teachme.studentservice.domain.exception.StudentAlreadyExistsException;

@Service
@RequiredArgsConstructor
public class CreateStudentService implements CreateStudentAdapter {

    private final StudentRepository studentRepository;
    private final EventPublisher eventPublisher;
    private final StudentCacheService studentCacheService;

    @Override
    @CacheEvict(value = "studentsList", allEntries = true)
    public StudentDto createStudent(CreateStudentCommand command) {
        var maybeStudent = studentRepository.findStudentByUserId(command.userId());

        if (maybeStudent.isPresent()) {
            throw new StudentAlreadyExistsException(command.userId());
        }

        var student = StudentFactory.create(command);

        var persistedStudent = studentRepository.saveStudent(student);

        studentCacheService.cacheStudent(persistedStudent);

        eventPublisher.publish(
                new StudentCreatedEvent(
                        student.getId(),
                        command.userId(),
                        student.getContactInformation().getName().name(),
                        student.getContactInformation().getName().surname()
                )
        );

        return new StudentDto(student);
    }
}
