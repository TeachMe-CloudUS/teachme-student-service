package us.cloud.teachme.studentservice.application.port;

import org.springframework.web.multipart.MultipartFile;

public interface StoragePort {

    String uploadFile(String fileUrl, MultipartFile file);

    void deleteFile(String fileUrl);
}
