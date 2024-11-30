package us.cloud.teachme.studentservice.application.adapter;

import us.cloud.teachme.studentservice.application.command.EnrollMeInCourseCommand;
import us.cloud.teachme.studentservice.application.command.EnrollStudentCommand;

public interface EnrollmentAdapter {

    void enrollStudentInCourse(EnrollStudentCommand command);

    void enrollStudentInCourse(EnrollMeInCourseCommand command);
}
