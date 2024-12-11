package us.cloud.teachme.studentservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.adapter.UpdateStudentAdapter;
import us.cloud.teachme.studentservice.application.command.UpdateMeCommand;
import us.cloud.teachme.studentservice.application.command.UpdateStudentCommand;
import us.cloud.teachme.studentservice.application.port.EventPublisher;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.event.StudentUpdateEvent;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundByUserIdException;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundException;
import us.cloud.teachme.studentservice.domain.model.ContactInformation;
import us.cloud.teachme.studentservice.domain.model.ProfileInformation;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UpdateStudentService implements UpdateStudentAdapter {

    private final StudentRepository studentRepository;
    private final EventPublisher eventPublisher;
    private final StudentCacheService studentCacheService;

    @Override
    @CacheEvict(value = "studentsList", allEntries = true)
    public void updateStudent(UpdateStudentCommand command) {
        var student = studentRepository.findStudentById(command.studentId()).orElseThrow(
                () -> new StudentNotFoundException(command.studentId())
        );

        student.setContactInformation(
                ContactInformation.create(
                        Objects.isNull(command.name()) ? student.getContactInformation().getName().name() : command.name(),
                        Objects.isNull(command.surname()) ? student.getContactInformation().getName().surname() : command.surname(),
                        student.getContactInformation().getEmail().email(),
                        Objects.isNull(command.phoneNumber()) ? student.getContactInformation().getPhoneNumber().value() : command.phoneNumber(),
                        Objects.isNull(command.country()) ? student.getContactInformation().getCountry() : command.country()
                )
        );

        student.setProfileInformation(
                ProfileInformation.create(
                        Objects.isNull(command.plan()) ? student.getProfileInformation().getPlan() : command.plan(),
                        Objects.isNull(command.language()) ? student.getProfileInformation().getLanguage().language() : command.language(),
                        student.getProfileInformation().getProfilePicture(),
                        Objects.isNull(command.bio()) ? student.getProfileInformation().getBio() : command.bio()
                )
        );

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
    }

    @Override
    public void updateStudent(UpdateMeCommand command) {
        var student = studentRepository.findStudentByUserId(command.userId()).orElseThrow(
                () -> new StudentNotFoundByUserIdException(command.userId())
        );

        student.setContactInformation(
                ContactInformation.create(
                        Objects.isNull(command.name()) ? student.getContactInformation().getName().name() : command.name(),
                        Objects.isNull(command.surname()) ? student.getContactInformation().getName().surname() : command.surname(),
                        student.getContactInformation().getEmail().email(),
                        Objects.isNull(command.phoneNumber()) ? student.getContactInformation().getPhoneNumber().value() : command.phoneNumber(),
                        Objects.isNull(command.country()) ? student.getContactInformation().getCountry() : command.country()
                )
        );

        student.setProfileInformation(
                ProfileInformation.create(
                        Objects.isNull(command.plan()) ? student.getProfileInformation().getPlan() : command.plan(),
                        Objects.isNull(command.language()) ? student.getProfileInformation().getLanguage().language() : command.language(),
                        student.getProfileInformation().getProfilePicture(),
                        Objects.isNull(command.bio()) ? student.getProfileInformation().getBio() : command.bio()
                )
        );

        var updatedStudent = studentRepository.saveStudent(student);

        eventPublisher.publish(
                new StudentUpdateEvent(
                        updatedStudent.getUserId().value(),
                        student.getId(),
                        command.phoneNumber(),
                        command.plan()
                )
        );
    }
}
