package us.cloud.teachme.studentservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.dto.StudentDto;
import us.cloud.teachme.studentservice.domain.model.Student;

@Service
@RequiredArgsConstructor
public class StudentCacheService {

    @CachePut(value = "students", key = "#student.id")
    public StudentDto cacheStudent(Student student) {
        // Method just used to create cache entry for student with the studentId
        return new StudentDto(student);
    }

    @CacheEvict(value = "students", key = "#student.id")
    public void clearCache(Student student) {
        // Method just used to clear cache entry for student with the studentId
    }
}
