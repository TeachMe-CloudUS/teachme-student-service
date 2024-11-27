package us.cloud.teachme.studentservice.application.command;

import us.cloud.teachme.studentservice.domain.model.valueObject.SubscriptionPlan;

public record CreateStudentCommand(
        String userId,
        String name,
        String surname,
        String email,
        String phoneNumber,
        String country,
        SubscriptionPlan plan,
        String language,
        String bio
) {
}