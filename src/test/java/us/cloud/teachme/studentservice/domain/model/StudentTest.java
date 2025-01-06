package us.cloud.teachme.studentservice.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import us.cloud.teachme.studentservice.application.command.CreateStudentCommand;
import us.cloud.teachme.studentservice.application.service.StudentFactory;
import us.cloud.teachme.studentservice.domain.exception.AlreadyEnrolledInCourseException;
import us.cloud.teachme.studentservice.domain.exception.CourseAlreadyCompletedException;
import us.cloud.teachme.studentservice.domain.exception.EnrollmentLimitReachedException;
import us.cloud.teachme.studentservice.domain.exception.NotEnrolledInCourseException;
import us.cloud.teachme.studentservice.domain.model.valueObject.SubscriptionPlan;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private Student student;

    @BeforeEach
    void setUp() {
        student = StudentFactory.create(createCommand("user123", SubscriptionPlan.BASIC));
    }

    @Test
    void shouldAddCourseToEnrolledCourses_whenNotAlreadyEnrolled() {
        // Act
        student.enrollInCourse("course101");

        // Assert
        assertTrue(student.getEnrolledCourses().contains("course101"),
                "Course should be added to enrolled courses");
    }

    @Test
    void shouldThrowException_whenAlreadyEnrolledInCourse() {
        // Arrange
        student.enrollInCourse("course101");

        // Act & Assert
        AlreadyEnrolledInCourseException exception = assertThrows(
                AlreadyEnrolledInCourseException.class,
                () -> student.enrollInCourse("course101"),
                "Should throw exception if already enrolled in course"
        );
        assertEquals("Already enrolled in course: course101", exception.getMessage());
    }

    @Test
    void shouldAddCourseToCompletedCourses_whenEnrolledButNotCompleted() {
        // Arrange
        student.enrollInCourse("course101");

        // Act
        student.completeCourse("course101");

        // Assert
        assertTrue(student.getCompletedCourses().contains("course101"),
                "Course should be added to completed courses");
    }

    @Test
    void shouldThrowException_whenCompletingCourseNotEnrolledIn() {
        // Act & Assert
        NotEnrolledInCourseException exception = assertThrows(
                NotEnrolledInCourseException.class,
                () -> student.completeCourse("course101"),
                "Should throw exception when completing course not enrolled in"
        );
        assertEquals("Not enrolled in course: course101", exception.getMessage());
    }

    @Test
    void shouldThrowException_whenCompletingCourseAlreadyCompleted() {
        // Arrange
        student.enrollInCourse("course101");
        student.completeCourse("course101");

        // Act & Assert
        CourseAlreadyCompletedException exception = assertThrows(
                CourseAlreadyCompletedException.class,
                () -> student.completeCourse("course101"),
                "Should throw exception when course is already completed"
        );
        assertEquals("Course already completed: course101", exception.getMessage());
    }

    @Test
    void shouldThrowException_whenEnrollmentLimitIsReached() {
        // Arrange
        for (int i = 1; i <= 10; i++) {
            student.enrollInCourse("course" + i);
        }

        // Act & Assert
        EnrollmentLimitReachedException exception = assertThrows(
                EnrollmentLimitReachedException.class,
                () -> student.enrollInCourse("course11"),
                "Should throw exception when enrollment limit is reached"
        );
        assertEquals("Enrollment limit reached for plan: BASIC", exception.getMessage());
    }

    @Test
    void shouldAllowMoreEnrollmentsForPremiumPlan() {
        // Arrange
        student = StudentFactory.create(createCommand("user456", SubscriptionPlan.PLATINUM));

        // Act
        for (int i = 1; i <= 10; i++) {
            student.enrollInCourse("course" + i);
        }

        // Assert
        assertEquals(10, student.getEnrolledCourses().size(),
                "Should allow up to 10 courses for PLATINUM plan without throwing exception");
    }

    @Test
    void shouldInitializeWithEmptyCourseLists() {
        // Assert
        assertNotNull(student.getEnrolledCourses(), "Enrolled courses list should not be null");
        assertNotNull(student.getCompletedCourses(), "Completed courses list should not be null");
        assertTrue(student.getEnrolledCourses().isEmpty(), "Enrolled courses list should be empty on creation");
        assertTrue(student.getCompletedCourses().isEmpty(), "Completed courses list should be empty on creation");
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
