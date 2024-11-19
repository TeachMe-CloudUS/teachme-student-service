package us.cloud.teachme.studentservice.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import us.cloud.teachme.studentservice.application.command.CompleteCourseCommand;
import us.cloud.teachme.studentservice.application.port.EventPublisher;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.event.CourseCompletedEvent;
import us.cloud.teachme.studentservice.domain.exception.DomainException;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundException;
import us.cloud.teachme.studentservice.domain.model.Student;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CompleteCourseServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private CompleteCourseService completeCourseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void completeStudentCourse_shouldCompleteCourseAndPublishEvent_whenStudentExistsAndEnrolledInCourse() {
        // Arrange
        String studentId = "student-id";
        String courseId = "course101";
        CompleteCourseCommand command = new CompleteCourseCommand(studentId, courseId);

        Student student = mock(Student.class);
        when(studentRepository.findStudentsById(studentId)).thenReturn(Optional.of(student));

        // Simulate successful course completion
        doNothing().when(student).completeCourse(courseId);

        // Act
        completeCourseService.completeStudentCourse(command);

        // Assert
        verify(student).completeCourse(courseId);
        verify(eventPublisher).publish(any(CourseCompletedEvent.class));
    }

    @Test
    void completeStudentCourse_shouldThrowStudentNotFoundException_whenStudentDoesNotExist() {
        // Arrange
        String studentId = "student-id";
        String courseId = "course101";
        CompleteCourseCommand command = new CompleteCourseCommand(studentId, courseId);

        when(studentRepository.findStudentsById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(StudentNotFoundException.class, () -> completeCourseService.completeStudentCourse(command));

        verify(eventPublisher, never()).publish(any(CourseCompletedEvent.class));
    }

    @Test
    void completeStudentCourse_shouldThrowDomainException_whenStudentNotEnrolledInCourse() {
        // Arrange
        String studentId = "student-id";
        String courseId = "course101";
        CompleteCourseCommand command = new CompleteCourseCommand(studentId, courseId);

        Student student = mock(Student.class);
        when(studentRepository.findStudentsById(studentId)).thenReturn(Optional.of(student));

        // Simulate "Not enrolled in course" exception
        doThrow(new DomainException("Not enrolled in course")).when(student).completeCourse(courseId);

        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> completeCourseService.completeStudentCourse(command));
        assertEquals("Not enrolled in course", exception.getMessage());

        verify(eventPublisher, never()).publish(any(CourseCompletedEvent.class));
    }

    @Test
    void completeStudentCourse_shouldThrowDomainException_whenCourseAlreadyCompleted() {
        // Arrange
        String studentId = "student-id";
        String courseId = "course101";
        CompleteCourseCommand command = new CompleteCourseCommand(studentId, courseId);

        Student student = mock(Student.class);
        when(studentRepository.findStudentsById(studentId)).thenReturn(Optional.of(student));

        // Simulate "Course already completed" exception
        doThrow(new DomainException("Course already completed")).when(student).completeCourse(courseId);

        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> completeCourseService.completeStudentCourse(command));
        assertEquals("Course already completed", exception.getMessage());

        verify(eventPublisher, never()).publish(any(CourseCompletedEvent.class));
    }
}
