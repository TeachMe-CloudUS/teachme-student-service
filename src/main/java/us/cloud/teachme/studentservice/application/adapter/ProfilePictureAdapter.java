package us.cloud.teachme.studentservice.application.adapter;

import us.cloud.teachme.studentservice.application.command.DeleteProfilePictureCommand;
import us.cloud.teachme.studentservice.application.command.UploadProfilePictureCommand;

public interface ProfilePictureAdapter {

    String upload(UploadProfilePictureCommand command);

    void delete(DeleteProfilePictureCommand command);
}
