package us.cloud.teachme.studentservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.cloud.teachme.studentservice.application.adapter.ProfilePictureAdapter;
import us.cloud.teachme.studentservice.application.command.DeleteProfilePictureCommand;
import us.cloud.teachme.studentservice.application.command.UploadProfilePictureCommand;
import us.cloud.teachme.studentservice.application.port.StoragePort;
import us.cloud.teachme.studentservice.application.port.StudentRepository;
import us.cloud.teachme.studentservice.domain.exception.StudentNotFoundByUserIdException;
import us.cloud.teachme.studentservice.domain.model.Student;

@Service
@RequiredArgsConstructor
public class ProfilePictureService implements ProfilePictureAdapter {

    private final StoragePort storagePort;
    private final StudentRepository studentRepository;

    @Override
    public String upload(UploadProfilePictureCommand command) {
        Student student = studentRepository.findStudentByUserId(command.userId())
                .orElseThrow(() -> new StudentNotFoundByUserIdException(command.userId()));

        String fileUrl = storagePort.uploadFile(command.userId(), command.image());

        student.getProfileInformation().setProfilePicture(fileUrl);

        studentRepository.saveStudent(student);

        return fileUrl;
    }

    @Override
    public void delete(DeleteProfilePictureCommand command) {
        Student student = studentRepository.findStudentByUserId(command.userId())
                .orElseThrow(() -> new StudentNotFoundByUserIdException(command.userId()));

        storagePort.deleteFile(command.userId());

        student.getProfileInformation().setProfilePicture(null);

        studentRepository.saveStudent(student);
    }
}
