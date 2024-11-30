package us.cloud.teachme.studentservice.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import us.cloud.teachme.studentservice.application.command.CreateStudentCommand;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundException;
import us.cloud.teachme.studentservice.domain.model.Student;
import us.cloud.teachme.studentservice.domain.model.valueObject.SubscriptionPlan;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CreateStudentCommand createCommand(String userId) {
        return new CreateStudentCommand(
                userId,
                "Max",
                "Mustermann",
                "test@gmail.com",
                "123-456-7890",
                "Germany",
                SubscriptionPlan.BASIC,
                "DE",
                "Heute ist ein guter Tag."
        );
    }

    @Test
    void getStudents_shouldReturnListOfStudentDtos_whenStudentsExist() {
        // Arrange
        Student student1 = StudentFactory.create(createCommand("user-id-1"));
        Student student2 = StudentFactory.create(createCommand("user-id-2"));
        when(studentRepository.findAllStudents()).thenReturn(Arrays.asList(student1, student2));

        // Act
        List<StudentDto> result = studentService.getStudents();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user-id-1", result.get(0).getUserId());
        assertEquals("user-id-2", result.get(1).getUserId());
        verify(studentRepository, times(1)).findAllStudents();
    }

    @Test
    void getStudentById_shouldReturnStudentDto_whenStudentExists() {
        // Arrange
        String userId = "user-id";
        Student student = StudentFactory.create(createCommand(userId));
        when(studentRepository.findStudentById(userId)).thenReturn(Optional.of(student));

        // Act
        StudentDto result = studentService.getStudentById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        verify(studentRepository, times(1)).findStudentById(userId);
    }

    @Test
    void getStudentById_shouldReturnNull_whenStudentDoesNotExist() {
        // Arrange
        String studentId = "nonexistent";
        when(studentRepository.findStudentById(studentId)).thenReturn(Optional.empty());

        // Act
        StudentDto result = studentService.getStudentById(studentId);

        // Assert
        assertNull(result);
        verify(studentRepository, times(1)).findStudentById(studentId);
    }

    @Test
    void deleteStudentById_shouldDeleteStudent_whenStudentExists() {
        // Arrange
        Student student = StudentFactory.create(createCommand("user-id"));
        when(studentRepository.findStudentById(student.getId())).thenReturn(Optional.of(student));

        // Act
        studentService.deleteStudentById(student.getId());

        when(studentRepository.findStudentById(student.getId())).thenReturn(Optional.empty());
        StudentDto result = studentService.getStudentById(student.getId());

        // Assert
        assertNull(result);
        verify(studentRepository, times(2)).findStudentById(student.getId());
    }

    @Test
    void deleteStudentById_shouldThrowError_whenStudentDoesNotExist() {
        // Arrange
        Student student = StudentFactory.create(createCommand("user-id"));
        when(studentRepository.findStudentById(student.getId())).thenReturn(Optional.empty());

        // Act & Act
        assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudentById(student.getId()));
    }
}
