package us.cloud.teachme.studentservice.infrastructure.messaging;

public enum KafkaTopics {
    STUDENT_UPDATED("student", "updated"),
    STUDENT_CREATED("student", "created"),
    STUDENT_ENROLLED("course", "enrolled"),
    COURSE_COMPLETED("course", "completed");

    private final String service;
    private final String entity;
    private final String action;

    KafkaTopics(String entity, String action) {
        this.service = "student-service";
        this.entity = entity;
        this.action = action;
    }

    public String getName() {
        return String.format("%s.%s.%s", service, entity, action);
    }
}
