package us.cloud.teachme.studentservice.domain.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = true)
public class StudentEnrollmentEvent extends DomainEvent {

    private static final String EVENT_NAME = "student.enrolled";

    private final String studentId;
    private final String courseId;
    private final LocalDateTime enrollmentDate;

    public StudentEnrollmentEvent(String studentId, String courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = LocalDateTime.now();
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
