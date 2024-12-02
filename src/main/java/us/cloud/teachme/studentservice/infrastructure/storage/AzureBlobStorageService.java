package us.cloud.teachme.studentservice.infrastructure.storage;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import us.cloud.teachme.studentservice.application.port.StoragePort;

@Service
@Profile("prod")
public class AzureBlobStorageService implements StoragePort {


    @Override
    public String uploadFile(String fileUrl, MultipartFile file) {
        return "";
    }

    @Override
    public void deleteFile(String fileUrl) {

    }
}
