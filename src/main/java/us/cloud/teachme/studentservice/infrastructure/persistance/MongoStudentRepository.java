package us.cloud.teachme.studentservice.infrastructure.persistance;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.model.Student;

@Service
public interface MongoStudentRepository extends MongoRepository<Student, String>, StudentRepository {
}
