package us.cloud.teachme.studentservice.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import us.cloud.teachme.authutils.config.AuthSecurityConfiguration;

@Configuration
@Import(AuthSecurityConfiguration.class)
public class SecurityConfig {

}
