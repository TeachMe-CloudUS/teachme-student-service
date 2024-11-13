package us.cloud.teachme.studentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.cloud.teachme.kafkaconfig.service.KafkaProducerService;
import us.cloud.teachme.studentservice.events.Event;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    public final KafkaProducerService kafkaProducerService;

    @Autowired
    public HelloWorldController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        kafkaProducerService.sendMessage(Event.USER_UPDATED, "Hello, World!");
        return "Hello, World!";
    }
}
