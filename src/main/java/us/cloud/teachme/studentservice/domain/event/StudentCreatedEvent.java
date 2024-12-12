package us.cloud.teachme.studentservice.domain.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import us.cloud.teachme.studentservice.infrastructure.messaging.KafkaTopics;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class StudentCreatedEvent extends DomainEvent {

    private static final String EVENT_NAME = KafkaTopics.STUDENT_CREATED.getName();

    private String studentId;
    private String userId;
    private LocalDateTime enrollmentDate;

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
