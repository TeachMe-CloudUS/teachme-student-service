package us.cloud.teachme.studentservice.domain.model.valueObject;

import lombok.Getter;

@Getter
public enum SubscriptionPlan {
    BASIC(10),
    GOLD(15),
    PLATINUM(Integer.MAX_VALUE);

    private final int maxCourses;

    SubscriptionPlan(int maxCourses) {
        this.maxCourses = maxCourses;
    }
}
