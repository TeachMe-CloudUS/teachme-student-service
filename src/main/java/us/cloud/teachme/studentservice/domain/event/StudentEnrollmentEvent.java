package us.cloud.teachme.studentservice.domain.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import us.cloud.teachme.studentservice.infrastructure.messaging.KafkaTopics;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = true)
public class StudentEnrollmentEvent extends DomainEvent {

    private static final String EVENT_NAME = KafkaTopics.STUDENT_ENROLLED.getName();

    private final String studentId;
    private final String userId;
    private final String courseId;
    private final LocalDateTime enrollmentDate;

    public StudentEnrollmentEvent(String studentId, String userId, String courseId) {
        this.studentId = studentId;
        this.userId = userId;
        this.courseId = courseId;
        this.enrollmentDate = LocalDateTime.now();
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
