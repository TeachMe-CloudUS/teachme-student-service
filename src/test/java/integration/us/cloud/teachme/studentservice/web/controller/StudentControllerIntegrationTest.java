package integration.us.cloud.teachme.studentservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import us.cloud.teachme.studentservice.StudentServiceApplication;
import us.cloud.teachme.studentservice.application.adapter.EnrollmentAdapter;
import us.cloud.teachme.studentservice.application.adapter.StudentAdapter;
import us.cloud.teachme.studentservice.application.command.EnrollStudentCommand;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.domain.model.SubscriptionPlan;
import us.cloud.teachme.studentservice.web.request.CreateStudentRequestDto;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {StudentServiceApplication.class})
@AutoConfigureMockMvc
class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentAdapter studentService;

    @Autowired
    private EnrollmentAdapter enrollmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setup() {
        studentService.getStudents().forEach(studentDto ->
                studentService.deleteStudentById(studentDto.getId())
        );

        if (Objects.nonNull(cacheManager.getCache("students"))) {
            cacheManager.getCache("students").clear();
        }
        if (Objects.nonNull(cacheManager.getCache("studentsList"))) {
            cacheManager.getCache("studentsList").clear();
        }
    }

    @Test
    void testCreateAndRetrieveStudent() throws Exception {
        // Arrange
        CreateStudentRequestDto request = new CreateStudentRequestDto("user1", "1234567890", SubscriptionPlan.BASIC);

        // Act
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        // Assert
        List<StudentDto> students = studentService.getStudents();
        assertThat(students).hasSize(1);

        StudentDto createdStudent = students.get(0);
        assertThat(createdStudent.getUserId()).isEqualTo("user1");
        assertThat(createdStudent.getPhoneNumber()).isEqualTo("1234567890");
        assertThat(createdStudent.getPlan()).isEqualTo(SubscriptionPlan.BASIC);
    }

    @Test
    void testEnrollStudentInCourse() throws Exception {
        // Arrange: Create and save a student
        CreateStudentRequestDto request = new CreateStudentRequestDto("user2", "0987654321", SubscriptionPlan.BASIC);
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        List<StudentDto> students = studentService.getStudents();
        String studentId = students.get(0).getId();
        String courseId = "course1";

        // Act
        mockMvc.perform(post("/api/students/{studentId}/courses/{courseId}/enroll", studentId, courseId))
                .andExpect(status().isOk());

        // Assert
        StudentDto enrolledStudent = studentService.getStudentById(studentId);
        assertThat(enrolledStudent.getEnrolledCourses()).contains(courseId);
    }

    @Test
    void testCompleteCourse() throws Exception {
        // Arrange
        CreateStudentRequestDto request = new CreateStudentRequestDto("user3", "1122334455", SubscriptionPlan.BASIC);
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        List<StudentDto> students = studentService.getStudents();
        String studentId = students.get(0).getId();
        String courseId = "course2";

        enrollmentService.enrollStudentInCourse(new EnrollStudentCommand(studentId, courseId));

        // Act
        mockMvc.perform(post("/api/students/{studentId}/courses/{courseId}/complete", studentId, courseId))
                .andExpect(status().isOk());

        // Assert
        StudentDto student = studentService.getStudentById(studentId);
        assertThat(student.getCompletedCourses()).contains(courseId);
    }

    @Test
    void testGetStudents() throws Exception {
        // Arrange
        CreateStudentRequestDto student1 = new CreateStudentRequestDto("user4", "5566778899", SubscriptionPlan.BASIC);
        CreateStudentRequestDto student2 = new CreateStudentRequestDto("user5", "9988776655", SubscriptionPlan.PLATINUM);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student2)))
                .andExpect(status().isCreated());

        // Act & Assert
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userId").value("user4"))
                .andExpect(jsonPath("$[1].userId").value("user5"));
    }
}
