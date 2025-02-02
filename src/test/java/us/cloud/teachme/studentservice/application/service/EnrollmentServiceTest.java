package us.cloud.teachme.studentservice.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import us.cloud.teachme.studentservice.application.command.EnrollStudentCommand;
import us.cloud.teachme.studentservice.application.port.CourseServiceClient;
import us.cloud.teachme.studentservice.application.port.EventPublisher;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.event.StudentEnrollmentEvent;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundException;
import us.cloud.teachme.studentservice.domain.model.Student;
import us.cloud.teachme.studentservice.domain.model.valueObject.UserId;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EnrollmentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private StudentCacheService studentCacheService;

    @Mock
    private CourseServiceClient courseServiceClient;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void enrollStudentInCourse_shouldEnrollStudentAndPublishEvent_whenStudentExists() {
        // Arrange
        String studentId = "student-id";
        String courseId = "course101";
        EnrollStudentCommand command = new EnrollStudentCommand(studentId, courseId);

        Student student = mock(Student.class);
        when(student.getUserId()).thenReturn(new UserId("user-id"));
        when(studentRepository.findStudentById(studentId)).thenReturn(Optional.of(student));
        when(courseServiceClient.validateCourse(any())).thenReturn(true);

        // Act
        enrollmentService.enrollStudentInCourse(command);

        // Assert
        verify(studentRepository).findStudentById(studentId);
        verify(student).enrollInCourse(courseId);
        verify(eventPublisher).publish(any(StudentEnrollmentEvent.class));
    }

    @Test
    void enrollStudentInCourse_shouldThrowStudentNotFoundException_whenStudentDoesNotExist() {
        // Arrange
        String studentId = "student-id";
        String courseId = "course101";
        EnrollStudentCommand command = new EnrollStudentCommand(studentId, courseId);

        when(studentRepository.findStudentById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(StudentNotFoundException.class, () -> enrollmentService.enrollStudentInCourse(command));

        verify(eventPublisher, never()).publish(any(StudentEnrollmentEvent.class));
    }
}
