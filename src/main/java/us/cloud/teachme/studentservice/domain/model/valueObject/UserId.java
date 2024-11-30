package us.cloud.teachme.studentservice.domain.model.valueObject;

public record UserId(String value) {
    public UserId {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID must not be null or empty");
        }
    }
}
