package us.cloud.teachme.studentservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.domain.model.Student;
import us.cloud.teachme.studentservice.infrastructure.persistance.MongoStudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final MongoStudentRepository studentRepository;

    public List<StudentDto> getStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(StudentDto::new)
                .collect(Collectors.toList());
    }

    public StudentDto getStudentById(String id) {
        Student student = studentRepository.findById(id).orElse(null);
        return student != null ? new StudentDto(student) : null;
    }
}
