package us.cloud.teachme.studentservice.application.port;

import us.cloud.teachme.studentservice.domain.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {

    List<Student> findAllStudents();

    Optional<Student> findStudentsById(String id);
}
