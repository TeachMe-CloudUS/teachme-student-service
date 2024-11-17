package us.cloud.teachme.studentservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.model.Student;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<StudentDto> getStudents() {
        List<Student> students = studentRepository.findAllStudents();
        return students.stream()
                .map(StudentDto::new)
                .collect(Collectors.toList());
    }

    public StudentDto getStudentById(String id) {
        Student student = studentRepository.findStudentsById(id).orElse(null);
        return student != null ? new StudentDto(student) : null;
    }

    public void deleteStudentById(String id) {
        studentRepository.deleteStudentById(id);
    }
}
