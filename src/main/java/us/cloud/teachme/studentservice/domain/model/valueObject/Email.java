package us.cloud.teachme.studentservice.domain.model.valueObject;

public record Email(String email) {
    public Email {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty!");
        }
    }
}
