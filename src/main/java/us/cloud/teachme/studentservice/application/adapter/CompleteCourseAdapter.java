package us.cloud.teachme.studentservice.application.adapter;

import us.cloud.teachme.studentservice.application.command.CompleteCourseCommand;
import us.cloud.teachme.studentservice.application.command.CompleteMyCourseCommand;

public interface CompleteCourseAdapter {

    void completeStudentCourse(CompleteCourseCommand command);

    void completeStudentCourse(CompleteMyCourseCommand command);
}
