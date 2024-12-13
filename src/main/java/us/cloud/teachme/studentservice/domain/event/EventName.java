package us.cloud.teachme.studentservice.domain.event;

public enum EventName {
    STUDENT_UPDATED("student", "updated"),
    STUDENT_CREATED("student", "created"),
    STUDENT_ENROLLED("course", "enrolled"),
    COURSE_COMPLETED("course", "completed");

    private final String service;
    private final String entity;
    private final String action;

    EventName(String entity, String action) {
        this.service = "student-service";
        this.entity = entity;
        this.action = action;
    }

    public String getName() {
        return String.format("%s.%s.%s", service, entity, action);
    }
}
