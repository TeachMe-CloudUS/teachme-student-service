package us.cloud.teachme.studentservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document("Student")
public class Student {

    @Id
    private String id;

    private String userId;

    private String phoneNumber;

    private SubscriptionPlan plan;

    private List<String> enrolledCourses;

    private List<String> completedCourses;

    private List<String> forumPosts;
}
