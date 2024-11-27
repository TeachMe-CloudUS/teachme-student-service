package us.cloud.teachme.studentservice.application.adapter;

import us.cloud.teachme.studentservice.application.dto.CourseDetailsCollection;
import us.cloud.teachme.studentservice.application.dto.StudentDto;

import java.util.List;

public interface StudentAdapter {

    List<StudentDto> getStudents();

    StudentDto getStudentById(String studentId);

    void deleteStudentById(String studentId);

    CourseDetailsCollection getEnrolledCourses(String studentId);

}
