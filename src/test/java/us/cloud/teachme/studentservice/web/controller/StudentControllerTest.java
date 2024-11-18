package us.cloud.teachme.studentservice.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import us.cloud.teachme.studentservice.application.command.CompleteCourseCommand;
import us.cloud.teachme.studentservice.application.command.CreateStudentCommand;
import us.cloud.teachme.studentservice.application.command.EnrollStudentCommand;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.application.service.CompleteCourseService;
import us.cloud.teachme.studentservice.application.service.CreateStudentService;
import us.cloud.teachme.studentservice.application.service.EnrollmentService;
import us.cloud.teachme.studentservice.application.service.StudentService;
import us.cloud.teachme.studentservice.domain.model.Student;
import us.cloud.teachme.studentservice.domain.model.SubscriptionPlan;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private EnrollmentService enrollmentService;

    @MockBean
    private CompleteCourseService completeCourseService;

    @MockBean
    private CreateStudentService createStudentService;

    @Test
    void testGetStudents() throws Exception {
        // Arrange
        Student mockStudent = new Student("user1", "1234567890", SubscriptionPlan.BASIC);
        mockStudent.setId("1");
        mockStudent.setEnrolledCourses(List.of("Course1"));
        mockStudent.setCompletedCourses(List.of("Course2"));
        mockStudent.setForumPosts(List.of("Post1"));

        when(studentService.getStudents()).thenReturn(List.of(new StudentDto(mockStudent)));

        // Act & Assert
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].userId").value("user1"))
                .andExpect(jsonPath("$[0].plan").value("BASIC"));
    }

    @Test
    void testGetStudentById() throws Exception {
        // Arrange
        Student mockStudent = new Student("user1", "1234567890", SubscriptionPlan.BASIC);
        mockStudent.setId("1");
        mockStudent.setEnrolledCourses(List.of("Course1"));
        mockStudent.setCompletedCourses(List.of("Course2"));
        mockStudent.setForumPosts(List.of("Post1"));

        when(studentService.getStudentById("1")).thenReturn(new StudentDto(mockStudent));

        // Act & Assert
        mockMvc.perform(get("/api/students/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.userId").value("user1"))
                .andExpect(jsonPath("$.plan").value("BASIC"));
    }

    @Test
    void testEnrollStudent() throws Exception {
        // Arrange
        doNothing().when(enrollmentService).enrollStudentInCourse(any(EnrollStudentCommand.class));

        // Act & Assert
        mockMvc.perform(post("/api/students/{studentId}/courses/{courseId}/enroll", "1", "course1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCompleteCourse() throws Exception {
        // Arrange
        doNothing().when(completeCourseService).completeStudentCourse(any(CompleteCourseCommand.class));

        // Act & Assert
        mockMvc.perform(post("/api/students/{studentId}/courses/{courseId}/complete", "1", "course1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateStudent() throws Exception {
        // Arrange
        doNothing().when(createStudentService).createStudent(any(CreateStudentCommand.class));

        // Act & Assert
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"user1\", \"phoneNumber\":\"1234567890\", \"plan\":\"BASIC\"}"))
                .andExpect(status().isCreated());
    }
}
