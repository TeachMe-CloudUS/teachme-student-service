package us.cloud.teachme.studentservice.application.command;

import us.cloud.teachme.studentservice.domain.model.valueObject.SubscriptionPlan;

public record UpdateMeCommand(
        String userId,
        String phoneNumber,
        SubscriptionPlan plan,
        String name,
        String surname,
        String country,
        String language,
        String bio
) {
}
