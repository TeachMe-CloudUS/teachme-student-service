package us.cloud.teachme.studentservice.infrastructure.storage;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "us.cloud.teachme.azure.blob-storage")
public class AzureConfigurationProperties {

    @NotBlank(message = "Blob Storage container name must be configured")
    private String containerName;

    @NotBlank(message = "Blob Storage connection string must be configured")
    private String connectionString;

}
