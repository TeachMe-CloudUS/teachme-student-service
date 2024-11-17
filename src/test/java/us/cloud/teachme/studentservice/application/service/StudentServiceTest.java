package us.cloud.teachme.studentservice.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.model.Student;

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

    @Test
    void getStudents_shouldReturnListOfStudentDtos_whenStudentsExist() {
        // Arrange
        String userId1 = "user-id-1";
        Student student1 = new Student(userId1, "123-456-7890", null);  // Assume appropriate values for constructor
        String userId2 = "user-id-2";
        Student student2 = new Student(userId2, "987-654-3210", null);
        when(studentRepository.findAllStudents()).thenReturn(Arrays.asList(student1, student2));

        // Act
        List<StudentDto> result = studentService.getStudents();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(userId1, result.get(0).getUserId());
        assertEquals(userId2, result.get(1).getUserId());
        verify(studentRepository, times(1)).findAllStudents();
    }

    @Test
    void getStudentById_shouldReturnStudentDto_whenStudentExists() {
        // Arrange
        String userId = "user-id";
        Student student = new Student(userId, "123-456-7890", null);
        when(studentRepository.findStudentsById(userId)).thenReturn(Optional.of(student));

        // Act
        StudentDto result = studentService.getStudentById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        verify(studentRepository, times(1)).findStudentsById(userId);
    }

    @Test
    void getStudentById_shouldReturnNull_whenStudentDoesNotExist() {
        // Arrange
        String studentId = "nonexistent";
        when(studentRepository.findStudentsById(studentId)).thenReturn(Optional.empty());

        // Act
        StudentDto result = studentService.getStudentById(studentId);

        // Assert
        assertNull(result);
        verify(studentRepository, times(1)).findStudentsById(studentId);
    }
}
