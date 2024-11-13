package us.cloud.teachme.studentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import us.cloud.teachme.kafkaconfig.service.KafkaUtils;
import us.cloud.teachme.studentservice.events.Event;

@Configuration
@Import(us.cloud.teachme.kafkaconfig.config.KafkaConfig.class)
public class KafkaConfig {

    @Bean
    public NewTopic userCreated() {
        return KafkaUtils.createTopic(Event.USER_CREATED);
    }

    @Bean
    public NewTopic userUpdated() {
        return KafkaUtils.createTopic(Event.USER_UPDATED);
    }
}
