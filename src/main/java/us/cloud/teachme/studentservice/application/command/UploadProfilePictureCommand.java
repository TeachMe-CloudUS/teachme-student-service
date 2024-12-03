package us.cloud.teachme.studentservice.application.command;

import org.springframework.web.multipart.MultipartFile;

public record UploadProfilePictureCommand(String userId, MultipartFile image) {

}