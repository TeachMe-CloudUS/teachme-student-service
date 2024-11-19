package us.cloud.teachme.studentservice.application.adapter;

import us.cloud.teachme.studentservice.application.command.CreateStudentCommand;
import us.cloud.teachme.studentservice.application.dto.StudentDto;

public interface CreateStudentAdapter {

    StudentDto createStudent(CreateStudentCommand command);
}
