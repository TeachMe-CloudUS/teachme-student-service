package us.cloud.teachme.studentservice.infrastructure.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;

@Configuration
@Profile("!test")
public class HttpClientsConfig {

    @Value("${teachme-course-service.url}")
    private String baseUrl;

    @Bean
    public RestClient courseServiceClient() {
        return RestClient.builder().baseUrl(baseUrl).build();
    }
}
