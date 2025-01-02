package us.cloud.teachme.studentservice.domain.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import us.cloud.teachme.studentservice.application.dto.CourseDetailsDto;
import us.cloud.teachme.studentservice.application.dto.StudentEventDto;

import java.time.Instant;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CourseCompletedEvent extends DomainEvent {

    private static final String EVENT_NAME = EventName.COURSE_COMPLETED.getName();

    private final StudentEventDto student;
    private final CourseDetailsDto course;
    private final Instant completionDate;

    public CourseCompletedEvent(StudentEventDto student, CourseDetailsDto course) {
        this.student = student;
        this.course = course;
        this.completionDate = Instant.now();
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
