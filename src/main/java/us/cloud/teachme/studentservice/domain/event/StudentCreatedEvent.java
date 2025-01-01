package us.cloud.teachme.studentservice.domain.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class StudentCreatedEvent extends DomainEvent {

    private static final String EVENT_NAME = EventName.STUDENT_CREATED.getName();

    private String studentId;
    private String userId;
    private String name;
    private String surname;
    private Instant timestamp;

    public StudentCreatedEvent(String studentId, String userId, String name, String surname) {
        this.studentId = studentId;
        this.userId = userId;
        this.timestamp = Instant.now();
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
