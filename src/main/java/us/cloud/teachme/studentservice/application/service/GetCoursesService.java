package us.cloud.teachme.studentservice.application.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.adapter.GetCoursesAdapter;
import us.cloud.teachme.studentservice.application.dto.CourseDetailsCollection;
import us.cloud.teachme.studentservice.application.port.CourseServiceClient;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.exception.ServiceUnavailableException;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundException;
import us.cloud.teachme.studentservice.domain.model.Student;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCoursesService implements GetCoursesAdapter {

    private final StudentRepository studentRepository;
    private final CourseServiceClient courseServiceClient;

    @Override
    public CourseDetailsCollection getEnrolledCourses(String studentId) {
        Student student = studentRepository.findStudentById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        var enrolledCourses = student.getEnrolledCourses();
        if (enrolledCourses.isEmpty()) {
            return new CourseDetailsCollection();
        }

        return getCourseDetails(enrolledCourses);
    }

    @Override
    public CourseDetailsCollection getEnrolledCoursesByUserId(String userId) {
        Student student = studentRepository.findStudentByUserId(userId)
                .orElseThrow(() -> new StudentNotFoundException(userId));

        var enrolledCourses = student.getEnrolledCourses();
        if (enrolledCourses.isEmpty()) {
            return new CourseDetailsCollection();
        }

        return getCourseDetails(enrolledCourses);
    }

    @Override
    public CourseDetailsCollection getCompletedCourses(String studentId) {
        Student student = studentRepository.findStudentById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        var enrolledCourses = student.getCompletedCourses();
        if (enrolledCourses.isEmpty()) {
            return new CourseDetailsCollection();
        }

        return getCourseDetails(enrolledCourses);
    }

    @Override
    public CourseDetailsCollection getCompletedCoursesByUserId(String userId) {
        Student student = studentRepository.findStudentByUserId(userId)
                .orElseThrow(() -> new StudentNotFoundException(userId));

        var enrolledCourses = student.getCompletedCourses();
        if (enrolledCourses.isEmpty()) {
            return new CourseDetailsCollection();
        }

        return getCourseDetails(enrolledCourses);
    }

    @CircuitBreaker(name = "student-service", fallbackMethod = "fallback")
    private CourseDetailsCollection getCourseDetails(List<String> enrolledCourses) {
        return courseServiceClient.getCourseDetails(enrolledCourses);
    }

    public CourseDetailsCollection fallback(List<String> enrolledCourses, Throwable throwable) {
        throw new ServiceUnavailableException(throwable.getMessage());
    }

}
