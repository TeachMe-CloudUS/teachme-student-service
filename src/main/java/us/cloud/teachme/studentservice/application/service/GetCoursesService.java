package us.cloud.teachme.studentservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.adapter.GetCoursesAdapter;
import us.cloud.teachme.studentservice.application.dto.CourseDetailsCollection;
import us.cloud.teachme.studentservice.application.port.CourseServiceClient;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundException;
import us.cloud.teachme.studentservice.domain.model.Student;

@Service
@RequiredArgsConstructor
public class GetCoursesService implements GetCoursesAdapter {

    private final StudentRepository studentRepository;
    private final CourseServiceClient courseServiceClient;

    @Override
    public CourseDetailsCollection getEnrolledCourses(String studentId) {
        Student student = studentRepository.findStudentById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        var enrolledCourses = student.getEnrolledCourses();
        if (enrolledCourses.isEmpty()) {
            return new CourseDetailsCollection();
        }

        return courseServiceClient.getCourseDetails(enrolledCourses);
    }

    @Override
    public CourseDetailsCollection getCompletedCourses(String studentId) {
        Student student = studentRepository.findStudentById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        var enrolledCourses = student.getCompletedCourses();
        if (enrolledCourses.isEmpty()) {
            return new CourseDetailsCollection();
        }


        return courseServiceClient.getCourseDetails(enrolledCourses);
    }

}
