package us.cloud.teachme.studentservice.domain.exception;

import org.springframework.http.HttpStatus;
import us.cloud.teachme.studentservice.domain.annotation.HttpStatusMapping;
import us.cloud.teachme.studentservice.domain.model.SubscriptionPlan;

@HttpStatusMapping(HttpStatus.FORBIDDEN)
public class EnrollmentLimitReachedException extends DomainException {

    public EnrollmentLimitReachedException(SubscriptionPlan plan) {
        super("Enrollment limit reached for plan: " + plan);
    }
}
