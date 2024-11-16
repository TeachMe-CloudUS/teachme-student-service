package us.cloud.teachme.studentservice.domain.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = true)
public class StudentCreatedEvent extends DomainEvent {

    private static final String EVENT_NAME = "student.created";

    private final String studentId;
    private final String userId;
    private final LocalDateTime enrollmentDate;

    public StudentCreatedEvent(String studentId, String userId) {
        this.studentId = studentId;
        this.userId = userId;
        this.enrollmentDate = LocalDateTime.now();
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
