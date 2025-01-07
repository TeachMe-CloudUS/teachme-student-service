package us.cloud.teachme.studentservice.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.cloud.teachme.studentservice.domain.model.ContactInformation;
import us.cloud.teachme.studentservice.domain.model.ProfileInformation;
import us.cloud.teachme.studentservice.domain.model.Student;
import us.cloud.teachme.studentservice.domain.model.valueObject.SubscriptionPlan;
import us.cloud.teachme.studentservice.infrastructure.persistance.MongoStudentRepository;

@Configuration
@Slf4j
public class DbSeedConfig {

    @Bean
    public CommandLineRunner createInitialStudent(MongoStudentRepository studentRepository) {
        return args -> {
            String initialUserId = "test-userid";
            if (!studentRepository.existsByUserId(initialUserId)) {
                ContactInformation contactInformation = ContactInformation.create(
                        "Max",
                        "Mustermann",
                        "max@mustermann.de",
                        "+49123456789",
                        "Germany");
                ProfileInformation profileInformation = ProfileInformation.create(
                        SubscriptionPlan.PLATINUM,
                        "DE",
                        "https://i.imgur.com/000000.jpg",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                );

                Student initialStudent = Student.createStudent(
                        "test-userid", contactInformation, profileInformation
                );

                studentRepository.save(initialStudent);

                log.info("Initial student created with userId: {}", initialUserId);
            } else {
                log.info("Initial student already exists with userId: {}", initialUserId);
            }
        };
    }
}
