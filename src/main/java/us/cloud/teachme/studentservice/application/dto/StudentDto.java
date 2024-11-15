package us.cloud.teachme.studentservice.application.dto;

import lombok.Data;
import us.cloud.teachme.studentservice.domain.model.Student;
import us.cloud.teachme.studentservice.domain.model.SubscriptionPlan;

import java.util.List;

@Data
public class StudentDto {

    private String id;

    private String userId;

    private String phoneNumber;

    private SubscriptionPlan plan;

    private List<String> enrolledCourses;

    private List<String> completedCourses;

    private List<String> forumPosts;

    public StudentDto(Student student) {
        this.id = student.getId();
        this.userId = student.getUserId();
        this.phoneNumber = student.getPhoneNumber();
        this.plan = student.getPlan();
        this.enrolledCourses = student.getEnrolledCourses();
        this.completedCourses = student.getCompletedCourses();
        this.forumPosts = student.getForumPosts();
    }
}
