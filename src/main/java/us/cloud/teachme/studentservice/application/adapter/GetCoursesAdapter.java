package us.cloud.teachme.studentservice.application.adapter;

import us.cloud.teachme.studentservice.application.dto.CourseDetailsCollection;

public interface GetCoursesAdapter {

    CourseDetailsCollection getEnrolledCourses(String studentId);

    CourseDetailsCollection getEnrolledCoursesByUserId(String userId);

    CourseDetailsCollection getCompletedCourses(String studentId);

    CourseDetailsCollection getCompletedCoursesByUserId(String userId);
}
