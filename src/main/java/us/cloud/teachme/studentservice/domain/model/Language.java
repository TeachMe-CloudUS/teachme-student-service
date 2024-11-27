package us.cloud.teachme.studentservice.domain.model;

public record Language(String language) {
    public Language {
        if (language == null || language.trim().isEmpty()) {
            throw new IllegalArgumentException("Language must not be empty!");
        }
    }
}
