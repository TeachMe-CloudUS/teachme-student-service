package us.cloud.teachme.studentservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.adapter.UpdateStudentAdapter;
import us.cloud.teachme.studentservice.application.command.UpdateStudentCommand;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.application.port.EventPublisher;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.event.StudentUpdateEvent;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundException;

@Service
@RequiredArgsConstructor
public class UpdateStudentService implements UpdateStudentAdapter {

    private final StudentRepository studentRepository;
    private final EventPublisher eventPublisher;
    private final StudentCacheService studentCacheService;

    @Override
    @CacheEvict(value = "studentsList", allEntries = true)
    public StudentDto updateStudent(UpdateStudentCommand command) {
        var student = studentRepository.findStudentsById(command.studentId()).orElseThrow(
                () -> new StudentNotFoundException(command.studentId())
        );

        student.setPlan(command.plan());
        if (command.phoneNumber() != null) {
            student.setPhoneNumber(command.phoneNumber());
        }

        var updatedStudent = studentRepository.saveStudent(student);

        studentCacheService.cacheStudent(updatedStudent);
        eventPublisher.publish(
                new StudentUpdateEvent(
                        updatedStudent.getUserId().value(),
                        student.getId(),
                        command.phoneNumber(),
                        command.plan()
                )
        );

        return new StudentDto(updatedStudent);

    }
}
