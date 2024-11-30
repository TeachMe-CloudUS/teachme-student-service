package us.cloud.teachme.studentservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.adapter.EnrollmentAdapter;
import us.cloud.teachme.studentservice.application.command.EnrollMeInCourseCommand;
import us.cloud.teachme.studentservice.application.command.EnrollStudentCommand;
import us.cloud.teachme.studentservice.application.port.CourseServiceClient;
import us.cloud.teachme.studentservice.application.port.EventPublisher;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.event.StudentEnrollmentEvent;
import us.cloud.teachme.studentservice.domain.exception.CourseNotFoundException;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundException;
import us.cloud.teachme.studentservice.domain.model.Student;

@Service
@RequiredArgsConstructor
public class EnrollmentService implements EnrollmentAdapter {

    private final StudentRepository studentRepository;
    private final EventPublisher eventPublisher;
    private final StudentCacheService studentCacheService;
    private final CourseServiceClient courseServiceClient;

    @Override
    @CacheEvict(value = "studentList", allEntries = true)
    public void enrollStudentInCourse(EnrollStudentCommand command) {
        Student student = studentRepository.findStudentById(command.studentId())
                .orElseThrow(() -> new StudentNotFoundException(command.studentId()));

        if (!courseServiceClient.validateCourse(command.courseId())) {
            throw new CourseNotFoundException(command.courseId());
        }

        student.enrollInCourse(command.courseId());

        var persistedStudent = studentRepository.saveStudent(student);

        studentCacheService.clearCache(persistedStudent);

        eventPublisher.publish(new StudentEnrollmentEvent(
                command.studentId(),
                student.getUserId().value(),
                command.courseId()
        ));
    }

    @Override
    public void enrollStudentInCourse(EnrollMeInCourseCommand command) {
        Student student = studentRepository.findStudentByUserId(command.userId())
                .orElseThrow(() -> new StudentNotFoundException(command.userId()));

        if (!courseServiceClient.validateCourse(command.courseId())) {
            throw new CourseNotFoundException(command.courseId());
        }

        student.enrollInCourse(command.courseId());

        var persistedStudent = studentRepository.saveStudent(student);

        studentCacheService.clearCache(persistedStudent);

        eventPublisher.publish(new StudentEnrollmentEvent(
                student.getId(),
                command.userId(),
                command.courseId()
        ));
    }
}
