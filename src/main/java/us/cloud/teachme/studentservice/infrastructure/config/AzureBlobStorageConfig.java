package us.cloud.teachme.studentservice.infrastructure.config;

import com.azure.core.util.Context;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.BlobServiceVersion;
import com.azure.storage.blob.models.PublicAccessType;
import com.azure.storage.blob.options.BlobContainerCreateOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.cloud.teachme.studentservice.infrastructure.storage.AzureConfigurationProperties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(AzureConfigurationProperties.class)
public class AzureBlobStorageConfig {

    private final AzureConfigurationProperties azureConfigurationProperties;

    @Bean
    public BlobContainerClient blobContainerClient() {
        var connectionString = azureConfigurationProperties.getConnectionString();
        var containerName = azureConfigurationProperties.getContainerName();

        var blobServiceClient = new BlobServiceClientBuilder()
                .serviceVersion(BlobServiceVersion.V2025_01_05)
                .connectionString(connectionString).buildClient();

        return blobServiceClient.createBlobContainerIfNotExistsWithResponse(
                containerName,
                new BlobContainerCreateOptions().setPublicAccessType(PublicAccessType.BLOB),
                Context.NONE
        ).getValue();
    }

}
