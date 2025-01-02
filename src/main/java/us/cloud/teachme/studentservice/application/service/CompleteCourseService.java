package us.cloud.teachme.studentservice.application.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.adapter.CompleteCourseAdapter;
import us.cloud.teachme.studentservice.application.command.CompleteCourseCommand;
import us.cloud.teachme.studentservice.application.command.CompleteMyCourseCommand;
import us.cloud.teachme.studentservice.application.dto.CourseDetailsCollection;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.application.port.CourseServiceClient;
import us.cloud.teachme.studentservice.application.port.EventPublisher;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.event.CourseCompletedEvent;
import us.cloud.teachme.studentservice.domain.exception.ServiceUnavailableException;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundByUserIdException;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundException;
import us.cloud.teachme.studentservice.domain.model.Student;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompleteCourseService implements CompleteCourseAdapter {

    private final StudentRepository studentRepository;
    private final CourseServiceClient courseServiceClient;
    private final EventPublisher eventPublisher;

    @Override
    @Caching(evict = {
            @CacheEvict(value = "student", key = "#command.studentId()"),
            @CacheEvict(value = "studentList", allEntries = true)
    })
    public void completeStudentCourse(CompleteCourseCommand command) {
        Student student = studentRepository.findStudentById(command.studentId())
                .orElseThrow(() -> new StudentNotFoundException(command.studentId()));

        student.completeCourse(command.courseId());

        studentRepository.saveStudent(student);

        var courseDetailsCollection = getCourseDetails(List.of(command.courseId()));

        eventPublisher.publish(new CourseCompletedEvent(
                new StudentDto(student),
                courseDetailsCollection.get(0)
        ));
    }

    @Override
    public void completeStudentCourse(CompleteMyCourseCommand command) {
        Student student = studentRepository.findStudentByUserId(command.userId())
                .orElseThrow(() -> new StudentNotFoundByUserIdException(command.userId()));

        student.completeCourse(command.courseId());

        studentRepository.saveStudent(student);

        var courseDetailsCollection = getCourseDetails(List.of(command.courseId()));

        eventPublisher.publish(new CourseCompletedEvent(
                new StudentDto(student),
                courseDetailsCollection.get(0)
        ));
    }

    @CircuitBreaker(name = "student-service", fallbackMethod = "fallback")
    private CourseDetailsCollection getCourseDetails(List<String> enrolledCourses) {
        return courseServiceClient.getCourseDetails(enrolledCourses);
    }

    public CourseDetailsCollection fallback(List<String> enrolledCourses, Throwable throwable) {
        throw new ServiceUnavailableException(throwable.getMessage());
    }
}
