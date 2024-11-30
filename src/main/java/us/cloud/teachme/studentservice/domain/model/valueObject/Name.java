package us.cloud.teachme.studentservice.domain.model.valueObject;

public record Name(String name, String surname) {
    public Name {
        if (name == null || name.trim().isEmpty() || surname == null || surname.trim().isEmpty()) {
            throw new IllegalArgumentException("Name/surname must not be empty!");
        }
    }
}
