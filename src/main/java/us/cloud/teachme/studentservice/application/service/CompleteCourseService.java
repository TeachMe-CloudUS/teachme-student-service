package us.cloud.teachme.studentservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.adapter.CompleteCourseAdapter;
import us.cloud.teachme.studentservice.application.command.CompleteCourseCommand;
import us.cloud.teachme.studentservice.application.port.EventPublisher;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.event.CourseCompletedEvent;
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
        Student student = studentRepository.findStudentsById(command.studentId())
                .orElseThrow(() -> new StudentNotFoundException("Student not found: " + command.studentId()));

        student.completeCourse(command.courseId());

        studentRepository.saveStudent(student);

        eventPublisher.publish(new CourseCompletedEvent(command.studentId(), command.courseId()));
    }
}
