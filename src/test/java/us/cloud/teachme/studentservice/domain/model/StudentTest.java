package us.cloud.teachme.studentservice.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import us.cloud.teachme.studentservice.domain.exception.DomainException;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student("user123", "123-456-7890", SubscriptionPlan.BASIC);
    }

    @Test
    void enrollInCourse_shouldAddCourseToEnrolledCourses_whenNotAlreadyEnrolled() {
        // Act
        student.enrollInCourse("course101");

        // Assert
        assertTrue(student.getEnrolledCourses().contains("course101"));
    }

    @Test
    void enrollInCourse_shouldThrowException_whenAlreadyEnrolledInCourse() {
        // Arrange
        student.enrollInCourse("course101");

        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> student.enrollInCourse("course101"));
        assertEquals("Already enrolled in course: course101", exception.getMessage());
    }

    @Test
    void completeCourse_shouldAddCourseToCompletedCourses_whenEnrolledButNotCompleted() {
        // Arrange
        student.enrollInCourse("course101");

        // Act
        student.completeCourse("course101");

        // Assert
        assertTrue(student.getCompletedCourses().contains("course101"));
    }

    @Test
    void completeCourse_shouldThrowException_whenNotEnrolledInCourse() {
        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> student.completeCourse("course101"));
        assertEquals("Not enrolled in course: course101", exception.getMessage());
    }

    @Test
    void completeCourse_shouldThrowException_whenCourseAlreadyCompleted() {
        // Arrange
        student.enrollInCourse("course101");
        student.completeCourse("course101");

        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> student.completeCourse("course101"));
        assertEquals("Course already completed: course101", exception.getMessage());
    }
}
