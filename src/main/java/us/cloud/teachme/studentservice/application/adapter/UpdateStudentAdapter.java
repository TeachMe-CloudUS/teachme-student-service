package us.cloud.teachme.studentservice.application.adapter;

import us.cloud.teachme.studentservice.application.command.UpdateStudentCommand;
import us.cloud.teachme.studentservice.application.dto.StudentDto;

public interface UpdateStudentAdapter {
    StudentDto updateStudent(UpdateStudentCommand command);

}
