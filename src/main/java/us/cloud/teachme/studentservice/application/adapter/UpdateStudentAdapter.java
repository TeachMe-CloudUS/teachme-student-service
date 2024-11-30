package us.cloud.teachme.studentservice.application.adapter;

import us.cloud.teachme.studentservice.application.command.UpdateMeCommand;
import us.cloud.teachme.studentservice.application.command.UpdateStudentCommand;

public interface UpdateStudentAdapter {

    void updateStudent(UpdateStudentCommand command);

    void updateStudent(UpdateMeCommand command);
}
