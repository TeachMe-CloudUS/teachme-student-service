package us.cloud.teachme.studentservice.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import us.cloud.teachme.studentservice.application.command.CreateStudentCommand;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.application.port.EventPublisher;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.event.StudentCreatedEvent;
import us.cloud.teachme.studentservice.domain.exception.StudentAlreadyExistsException;
import us.cloud.teachme.studentservice.domain.model.Student;
import us.cloud.teachme.studentservice.domain.model.valueObject.SubscriptionPlan;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CreateStudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private StudentCacheService studentCacheService;

    @InjectMocks
    private CreateStudentService createStudentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createStudent_shouldSaveStudentAndPublishEvent_whenStudentDoesNotExist() {
        // Arrange
        String userId = "user-id";
        String phoneNumber = "123-456-7890";
        SubscriptionPlan plan = SubscriptionPlan.PLATINUM;

        CreateStudentCommand command = new CreateStudentCommand(
                userId,
                "Max",
                "Mustermann",
                "test@gmail.com",
                phoneNumber,
                "Germany",
                plan,
                "DE",
                "Heute ist ein guter Tag."
        );

        var savedStudent = StudentFactory.create(command);

        when(studentRepository.saveStudent(any(Student.class))).thenReturn(savedStudent);
        when(studentCacheService.cacheStudent(any(Student.class))).thenReturn(new StudentDto(savedStudent));

        // Act
        createStudentService.createStudent(command);

        // Assert
        verify(studentRepository).saveStudent(any(Student.class));
        verify(eventPublisher).publish(any(StudentCreatedEvent.class));
    }

    @Test
    void createStudent_shouldThrowStudentAlreadyExistsException_whenStudentAlreadyExists() {
        // Arrange
        String userId = "user-id";
        String phoneNumber = "123-456-7890";
        SubscriptionPlan plan = SubscriptionPlan.BASIC;

        CreateStudentCommand command = new CreateStudentCommand(
                userId,
                "Max",
                "Mustermann",
                "test@gmail.com",
                phoneNumber,
                "Germany",
                plan,
                "DE",
                "Heute ist ein guter Tag."
        );

        Student student = StudentFactory.create(command);

        when(studentRepository.findStudentByUserId(any(String.class))).thenReturn(Optional.of(student));

        // Act & Assert
        assertThrows(StudentAlreadyExistsException.class, () -> createStudentService.createStudent(command));

        verify(eventPublisher, never()).publish(any(StudentCreatedEvent.class));
    }
}
