package us.cloud.teachme.studentservice.infrastructure.storage;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import us.cloud.teachme.studentservice.application.port.StoragePort;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AzureBlobStorageService implements StoragePort {

    @Value("${teachme.blob.url-rewrite-host:}")
    private String urlRewriteHost;

    private final BlobContainerClient blobContainerClient;

    @Override
    public String uploadFile(String fileUrl, MultipartFile file) {
        var blobClient = blobContainerClient.getBlobClient(fileUrl);
        try {
            blobClient.upload(file.getInputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }

        return rewriteBlobUrl(blobClient.getBlobUrl());
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

    private String rewriteBlobUrl(String blobUrl) {
        if (urlRewriteHost != null && !urlRewriteHost.isBlank()) {
            String currentScheme = blobUrl.startsWith("https://") ? "https://" : "http://";
            String port = "";
            String hostWithPortRegex = "^(https?://)([^:/]+)(:\\d+)?";
            var matcher = java.util.regex.Pattern.compile(hostWithPortRegex).matcher(blobUrl);
            if (matcher.find()) {
                port = matcher.group(3) != null ? matcher.group(3) : "";
            }
            return blobUrl.replaceFirst(hostWithPortRegex, currentScheme + urlRewriteHost + port);
        }
        return blobUrl;
    }
}
