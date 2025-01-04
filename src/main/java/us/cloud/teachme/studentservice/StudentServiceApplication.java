package us.cloud.teachme.studentservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@OpenAPIDefinition(
        info = @Info(
                title = "Student Microservice API",
                version = "1.0",
                description = "API for managing student data for the teachme platform",
                contact = @Contact(name = "API Support", email = "ramnak@alum.us.es")
        ),
        servers = @Server(url = "${GATEWAY_SERVER_URL:http://localhost:8888}")
)
@EnableCaching
@SpringBootApplication
@EnableMongoRepositories
@EnableKafka
public class StudentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentServiceApplication.class, args);
    }

}