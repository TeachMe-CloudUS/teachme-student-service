package us.cloud.teachme.teaching_service.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import us.cloud.teachme.authutils.JwtFilterConfig;

@Configuration
@Import(JwtFilterConfig.class)
public class SecurityConfig {

}