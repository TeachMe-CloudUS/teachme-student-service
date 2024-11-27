package us.cloud.teachme.studentservice.domain.model;

public record PhoneNumber(String value) {
    public PhoneNumber {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("PhoneNumber must not be empty!");
        }
    }
}
