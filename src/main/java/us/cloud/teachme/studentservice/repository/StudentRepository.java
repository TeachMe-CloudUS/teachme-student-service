package us.cloud.teachme.studentservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import us.cloud.teachme.studentservice.model.Student;

public interface StudentRepository extends MongoRepository<Student, String> {
}
