package us.cloud.teachme.studentservice.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import us.cloud.teachme.studentservice.infrastructure.messaging.events.UserDeletedEvent;
import us.cloud.teachme.studentservice.infrastructure.persistance.MongoStudentRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventConsumer {

    private final ObjectMapper mapper;
    private final MongoStudentRepository repository;

    @KafkaListener(topics = {
            "auth-service.user.deleted",
    })
    public void consume(@Payload String message) {
        try {
            var event = mapper.readValue(message, UserDeletedEvent.class);

            var user = repository.findByUserId(event.getId());
            user.ifPresent(repository::delete);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}