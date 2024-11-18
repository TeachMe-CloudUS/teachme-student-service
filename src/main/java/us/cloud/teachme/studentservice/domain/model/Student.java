package us.cloud.teachme.studentservice.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import us.cloud.teachme.studentservice.domain.exception.AlreadyEnrolledInCourseException;
import us.cloud.teachme.studentservice.domain.exception.CourseAlreadyCompletedException;
import us.cloud.teachme.studentservice.domain.exception.NotEnrolledInCourseException;

import java.util.ArrayList;
import java.util.List;

@Data
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

    public Student() {
        this.enrolledCourses = new ArrayList<>();
        this.completedCourses = new ArrayList<>();
        this.forumPosts = new ArrayList<>();
    }

    public Student(String userId, String phoneNumber, SubscriptionPlan plan) {
        this();

        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.plan = plan;
    }

    public void enrollInCourse(String courseId) {
        if (enrolledCourses.contains(courseId)) {
            throw new AlreadyEnrolledInCourseException(courseId);
        }

        enrolledCourses.add(courseId);
    }

    public void completeCourse(String courseId) {
        if (!enrolledCourses.contains(courseId)) {
            throw new NotEnrolledInCourseException(courseId);
        }

        if (completedCourses.contains(courseId)) {
            throw new CourseAlreadyCompletedException(courseId);
        }

        completedCourses.add(courseId);
    }
}
