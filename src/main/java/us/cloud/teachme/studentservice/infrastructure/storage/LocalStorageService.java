package us.cloud.teachme.studentservice.infrastructure.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import us.cloud.teachme.studentservice.application.port.StoragePort;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


@Service
@Profile("local")
public class LocalStorageService implements StoragePort {

    @Value("${storage.local.directory}")
    private String localDirectory;

    @Override
    public String uploadFile(String fileUrl, MultipartFile file) {
        File userDirectory = new File(localDirectory, fileUrl);
        if (!userDirectory.exists()) {
            userDirectory.mkdirs();
        }

        if (Objects.isNull(file.getOriginalFilename())) {
            throw new IllegalArgumentException("File name is empty");
        }

        File destinationFile = new File(userDirectory, file.getOriginalFilename());
        try {
            file.transferTo(destinationFile);
            return destinationFile.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @Override
    public void deleteFile(String fileUrl) {
        File file = new File(fileUrl);
        if (file.exists()) {
            if (!file.delete()) {
                throw new RuntimeException("Failed to upload file");
            }
        }
    }
}
