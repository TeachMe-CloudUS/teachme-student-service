package us.cloud.teachme.studentservice.application.adapter;

import us.cloud.teachme.studentservice.application.command.CreateStudentCommand;

public interface CreateStudentAdapter {

    void createStudent(CreateStudentCommand command);
}
