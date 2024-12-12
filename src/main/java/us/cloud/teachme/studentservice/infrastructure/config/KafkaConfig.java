package us.cloud.teachme.studentservice.infrastructure.config;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import us.cloud.teachme.studentservice.infrastructure.messaging.KafkaTopics;

@EnableKafka
@Configuration
@AllArgsConstructor
@Import(us.cloud.teachme.kafkaconfig.config.KafkaConfig.class)
public class KafkaConfig {

    @Bean
    public NewTopic updateStudentTopic() {
        return new NewTopic(KafkaTopics.STUDENT_UPDATED.getName(), 1, (short) 1);
    }

    @Bean
    public NewTopic completeCourseTopic() {
        return new NewTopic(KafkaTopics.COURSE_COMPLETED.getName(), 1, (short) 1);
    }
}