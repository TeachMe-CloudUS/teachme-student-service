package us.cloud.teachme.studentservice.application.command;

import us.cloud.teachme.studentservice.domain.model.valueObject.SubscriptionPlan;

public record UpdateStudentCommand(String studentId, String phoneNumber, SubscriptionPlan plan) {
}