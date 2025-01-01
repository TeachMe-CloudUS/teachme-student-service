package us.cloud.teachme.studentservice.application.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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

        String fileUrl = uploadFile(command);

        student.getProfileInformation().setProfilePicture(fileUrl);

        studentRepository.saveStudent(student);

        return fileUrl;
    }

    @CircuitBreaker(name = "student-service", fallbackMethod = "uploadFallback")
    private String uploadFile(UploadProfilePictureCommand command) {
        return storagePort.uploadFile(command.userId(), command.image());
    }

    public String uploadFallback(UploadProfilePictureCommand command, Throwable throwable) {
        throw new StudentNotFoundByUserIdException(throwable.getMessage());
    }

    @Override
    public void delete(DeleteProfilePictureCommand command) {
        Student student = studentRepository.findStudentByUserId(command.userId())
                .orElseThrow(() -> new StudentNotFoundByUserIdException(command.userId()));

        deleteFile(command);

        student.getProfileInformation().setProfilePicture(null);

        studentRepository.saveStudent(student);
    }

    @CircuitBreaker(name = "student-service", fallbackMethod = "deleteFallback")
    private void deleteFile(DeleteProfilePictureCommand command) {
        storagePort.deleteFile(command.userId());
    }

    public String deleteFallback(DeleteProfilePictureCommand command, Throwable throwable) {
        throw new StudentNotFoundByUserIdException(throwable.getMessage());
    }
}
