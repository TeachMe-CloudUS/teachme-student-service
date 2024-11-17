package us.cloud.teachme.studentservice.application.command;

import us.cloud.teachme.studentservice.domain.model.SubscriptionPlan;

public record CreateStudentCommand(String userId, String phoneNumber, SubscriptionPlan plan) {

}