package us.cloud.teachme.studentservice.application.adapter;

import us.cloud.teachme.studentservice.application.command.CompleteCourseCommand;

public interface CompleteCourseAdapter {

    void completeStudentCourse(CompleteCourseCommand command);
}
