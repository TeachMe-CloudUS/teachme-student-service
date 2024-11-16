package us.cloud.teachme.studentservice.application.port;

import us.cloud.teachme.studentservice.domain.event.DomainEvent;

public interface EventPublisher {

    void publish(DomainEvent event);
}
