package us.cloud.teachme.studentservice.infrastructure.persistance;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.model.Student;

import java.util.List;
import java.util.Optional;

@Service
public interface MongoStudentRepository extends MongoRepository<Student, String>, StudentRepository {


    @Override
    default List<Student> findAllStudents() {
        return findAll();
    }

    @Override
    default Optional<Student> findStudentsById(String id) {
        return findById(id);
    }

    @Override
    default Student saveStudent(Student student) {
        return save(student);
    }
}