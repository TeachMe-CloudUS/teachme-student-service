package us.cloud.teachme.studentservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.adapter.CompleteCourseAdapter;
import us.cloud.teachme.studentservice.application.command.CompleteCourseCommand;
import us.cloud.teachme.studentservice.application.command.CompleteMyCourseCommand;
import us.cloud.teachme.studentservice.application.port.EventPublisher;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.event.CourseCompletedEvent;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundByUserIdException;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundException;
import us.cloud.teachme.studentservice.domain.model.Student;

@Service
@RequiredArgsConstructor
public class CompleteCourseService implements CompleteCourseAdapter {

    private final StudentRepository studentRepository;

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

        eventPublisher.publish(new CourseCompletedEvent(
                command.studentId(),
                student.getUserId().value(),
                command.courseId()
        ));
    }

    @Override
    public void completeStudentCourse(CompleteMyCourseCommand command) {
        Student student = studentRepository.findStudentByUserId(command.userId())
                .orElseThrow(() -> new StudentNotFoundByUserIdException(command.userId()));

        student.completeCourse(command.courseId());

        studentRepository.saveStudent(student);

        eventPublisher.publish(new CourseCompletedEvent(
                student.getId(),
                command.userId(),
                command.courseId()
        ));
    }
}
