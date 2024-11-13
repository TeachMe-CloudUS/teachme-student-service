package us.cloud.teachme.studentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import us.cloud.teachme.studentservice.events.Event;

@SpringBootApplication
public class StudentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentServiceApplication.class, args);
    }

    @KafkaListener(topics = Event.USER_UPDATED)
    public void consume(String message) {
        System.out.println(message);
    }
}