package us.cloud.teachme.studentservice.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import us.cloud.teachme.studentservice.domain.model.SubscriptionPlan;

@Getter
@AllArgsConstructor
public class StudentUpdateEvent extends DomainEvent {

    private static final String EVENT_NAME = "student.updated";

    private final String studentId;
    private final String userId;
    private final String phoneNumber;
    private final SubscriptionPlan plan;

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
