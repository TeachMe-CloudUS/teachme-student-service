package us.cloud.teachme.studentservice.infrastructure.storage;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import us.cloud.teachme.studentservice.application.port.StoragePort;

import java.io.IOException;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AzureBlobStorageService implements StoragePort {

    private final BlobContainerClient blobContainerClient;

    @Override
    public String uploadFile(String fileUrl, MultipartFile file) {
        var blobClient = blobContainerClient.getBlobClient(fileUrl);
        try {
            blobClient.upload(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }

        return blobClient.getBlobUrl();
    }

    @Override
    public void deleteFile(String fileUrl) {
        var blobClient = getBlobClient(fileUrl);
        blobClient.delete();
    }

    private BlobClient getBlobClient(String blobName) {
        var blobClient = blobContainerClient.getBlobClient(blobName);
        if (Boolean.FALSE.equals(blobClient.exists())) {
            throw new IllegalArgumentException("No blob exists with given name");
        }
        return blobClient;
    }
}
