package us.cloud.teachme.studentservice.application.service;

import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.command.CreateStudentCommand;
import us.cloud.teachme.studentservice.domain.model.Student;
import us.cloud.teachme.studentservice.domain.model.ContactInformation;
import us.cloud.teachme.studentservice.domain.model.ProfileInformation;

public class StudentFactory {

   public static Student create(CreateStudentCommand command) {
       return Student.createStudent(
               command.userId(),
               ContactInformation.create(
                       command.name(),
                       command.surname(),
                       command.email(),
                       command.phoneNumber(),
                       command.country()
               ),
               ProfileInformation.create(
                       command.plan(),
                       command.language(),
                       null,
                       command.bio()
               )
       );
   }
}
