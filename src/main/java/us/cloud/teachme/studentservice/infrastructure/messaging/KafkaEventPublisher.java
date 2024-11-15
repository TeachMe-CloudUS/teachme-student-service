package us.cloud.teachme.studentservice.infrastructure.messaging;

import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.port.EventPublisher;
import us.cloud.teachme.studentservice.domain.event.DomainEvent;

@Service
public class KafkaEventPublisher implements EventPublisher {

    @Override
    public void publish(DomainEvent event) {

    }
}
