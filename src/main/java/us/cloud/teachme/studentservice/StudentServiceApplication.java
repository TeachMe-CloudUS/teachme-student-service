package us.cloud.teachme.studentservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@OpenAPIDefinition(
        info = @Info(
                title = "Student Microservice API",
                version = "1.0",
                description = "API for managing student data for the teachme platform",
                contact = @Contact(name = "API Support", email = "ramnak@alum.us.es")
        )
)
@SpringBootApplication
@EnableMongoRepositories
public class StudentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentServiceApplication.class, args);
    }

}