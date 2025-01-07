package us.cloud.teachme.studentservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.adapter.StudentAdapter;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundByUserIdException;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundException;
import us.cloud.teachme.studentservice.domain.model.Student;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService implements StudentAdapter {

    private final StudentRepository studentRepository;

    @Override
    @Cacheable("studentsList")
    public List<StudentDto> getStudents() {
        List<Student> students = studentRepository.findAllStudents();
        return students.stream()
                .map(StudentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "students", key = "#studentId")
    public StudentDto getStudentById(String studentId) {
        Student student = studentRepository.findStudentById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
        return student != null ? new StudentDto(student) : null;
    }

    @Override
    public StudentDto getStudentByUserId(String userId) {
        Student student = studentRepository.findStudentByUserId(userId)
                .orElseThrow(() -> new StudentNotFoundByUserIdException(userId));
        return new StudentDto(student);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "students", key = "#studentId"),
            @CacheEvict(value = "studentsList", allEntries = true)
    })
    public void deleteStudentById(String studentId) {
        studentRepository.findStudentById(studentId).orElseThrow(
                () -> new StudentNotFoundException(studentId)
        );

        studentRepository.deleteStudentById(studentId);
    }
}
