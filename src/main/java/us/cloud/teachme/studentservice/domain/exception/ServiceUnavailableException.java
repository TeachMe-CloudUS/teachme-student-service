package us.cloud.teachme.studentservice.domain.exception;

import org.springframework.http.HttpStatus;
import us.cloud.teachme.studentservice.domain.annotation.HttpStatusMapping;

@HttpStatusMapping(HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceUnavailableException extends DomainException {

    public ServiceUnavailableException(String message) {
        super(message);
    }
}
