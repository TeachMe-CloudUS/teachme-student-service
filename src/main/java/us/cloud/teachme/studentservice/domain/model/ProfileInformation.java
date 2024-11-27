package us.cloud.teachme.studentservice.domain.model;

import lombok.Data;

@Data
public class ProfileInformation {

    private SubscriptionPlan plan;
    private Language language;
    private String profilePicture;
    private String bio;

    private ProfileInformation(SubscriptionPlan plan, Language language , String profilePicture, String bio) {
        this.plan = plan;
        this.profilePicture = profilePicture;
        this.bio = bio;
        this.language = language;
    }

    public static ProfileInformation create(SubscriptionPlan plan, String language, String profilePicture, String bio) {
        Language validatedLanguage = new Language(language);
        return new ProfileInformation(plan, validatedLanguage, profilePicture, bio);
    }
}
