package us.cloud.teachme.studentservice.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import us.cloud.teachme.studentservice.application.adapter.CompleteCourseAdapter;
import us.cloud.teachme.studentservice.application.adapter.CreateStudentAdapter;
import us.cloud.teachme.studentservice.application.adapter.EnrollmentAdapter;
import us.cloud.teachme.studentservice.application.adapter.StudentAdapter;
import us.cloud.teachme.studentservice.application.command.CompleteCourseCommand;
import us.cloud.teachme.studentservice.application.command.CreateStudentCommand;
import us.cloud.teachme.studentservice.application.command.EnrollStudentCommand;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.application.service.StudentFactory;
import us.cloud.teachme.studentservice.domain.exception.StudentAlreadyExistsException;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundException;
import us.cloud.teachme.studentservice.domain.model.Student;
import us.cloud.teachme.studentservice.domain.model.valueObject.SubscriptionPlan;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentAdapter studentService;

    @MockBean
    private EnrollmentAdapter enrollmentService;

    @MockBean
    private CompleteCourseAdapter completeCourseService;

    @MockBean
    private CreateStudentAdapter createStudentService;

    @Test
    void testGetStudents() throws Exception {
        // Arrange
        Student mockStudent = StudentFactory.create(createCommand("user1", SubscriptionPlan.BASIC));
        mockStudent.setId("1");

        when(studentService.getStudents()).thenReturn(List.of(new StudentDto(mockStudent)));

        // Act & Assert
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].userId").value("user1"))
                .andExpect(jsonPath("$[0].profileInformation.plan").value("BASIC"));
    }

    @Test
    void testGetStudentById_Success() throws Exception {
        // Arrange
        Student mockStudent = StudentFactory.create(createCommand("user1", SubscriptionPlan.BASIC));
        mockStudent.setId("1");

        when(studentService.getStudentById("1")).thenReturn(new StudentDto(mockStudent));

        // Act & Assert
        mockMvc.perform(get("/api/students/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.userId").value("user1"))
                .andExpect(jsonPath("$.profileInformation.plan").value("BASIC"));
    }

    @Test
    void testGetStudentById_NotFound() throws Exception {
        // Arrange
        when(studentService.getStudentById("1")).thenThrow(new StudentNotFoundException("1"));

        // Act & Assert
        mockMvc.perform(get("/api/students/{id}", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.toString()));
    }

    @Test
    void testEnrollStudent_Success() throws Exception {
        // Arrange
        doNothing().when(enrollmentService).enrollStudentInCourse(any(EnrollStudentCommand.class));

        // Act & Assert
        mockMvc.perform(post("/api/students/{studentId}/courses/{courseId}/enroll", "1", "course1"))
                .andExpect(status().isOk());
    }

    @Test
    void testEnrollStudent_NotFound() throws Exception {
        // Arrange
        doThrow(new StudentNotFoundException("1")).when(enrollmentService).enrollStudentInCourse(any(EnrollStudentCommand.class));

        // Act & Assert
        mockMvc.perform(post("/api/students/{studentId}/courses/{courseId}/enroll", "1", "course1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.toString()));
    }

    @Test
    void testCompleteCourse_Success() throws Exception {
        // Arrange
        doNothing().when(completeCourseService).completeStudentCourse(any(CompleteCourseCommand.class));

        // Act & Assert
        mockMvc.perform(post("/api/students/{studentId}/courses/{courseId}/complete", "1", "course1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCompleteCourse_NotFound() throws Exception {
        // Arrange
        doThrow(new StudentNotFoundException("1")).when(completeCourseService).completeStudentCourse(any(CompleteCourseCommand.class));

        // Act & Assert
        mockMvc.perform(post("/api/students/{studentId}/courses/{courseId}/complete", "1", "course1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.toString()));
    }

    @Test
    void testCreateStudent_Success() throws Exception {
        // Arrange
        Student mockStudent = StudentFactory.create(createCommand("user1", SubscriptionPlan.BASIC));
        doReturn(new StudentDto(mockStudent)).when(createStudentService).createStudent(any(CreateStudentCommand.class));

        String content = "{ \"userId\": \"user1\", \"name\": \"Max\", \"surname\":\"Mustermann\", \"email\": \"max.mustermann@gmail.com\", \"phoneNumber\": \"+584127772190\", \"language\": \"spanish\", \"plan\": \"BASIC\" }";

        // Act & Assert
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateStudent_AlreadyExists() throws Exception {
        // Arrange
        doThrow(new StudentAlreadyExistsException("Message")).when(createStudentService).createStudent(any(CreateStudentCommand.class));

        String content = "{ \"userId\": \"user1\", \"name\": \"Max\", \"surname\":\"Mustermann\", \"email\": \"max.mustermann@gmail.com\", \"phoneNumber\": \"+584127772190\", \"language\": \"spanish\", \"plan\": \"BASIC\" }";

        // Act & Assert
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("409 CONFLICT"));
    }

    @Test
    void testCreateStudent_ValidationError() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"phoneNumber\":\"1234567890\", \"plan\":\"BASIC\"}")) // Missing userId
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.userId").value("User ID is required"));
    }

    private CreateStudentCommand createCommand(String userId, SubscriptionPlan plan) {
        return new CreateStudentCommand(
                userId,
                "Max",
                "Mustermann",
                "test@gmail.com",
                "123-456-7890",
                "Germany",
                plan,
                "DE",
                "Heute ist ein guter Tag."
        );
    }
}