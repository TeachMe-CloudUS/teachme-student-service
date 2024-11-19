package us.cloud.teachme.studentservice.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import us.cloud.teachme.studentservice.domain.exception.AlreadyEnrolledInCourseException;
import us.cloud.teachme.studentservice.domain.exception.CourseAlreadyCompletedException;
import us.cloud.teachme.studentservice.domain.exception.EnrollmentLimitReachedException;
import us.cloud.teachme.studentservice.domain.exception.NotEnrolledInCourseException;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("Student")
public class Student {

    private List<String> enrolledCourses = new ArrayList<>();
    private List<String> completedCourses = new ArrayList<>();
    private List<String> forumPosts = new ArrayList<>();

    @Id
    private String id;

    private UserId userId;
    private PhoneNumber phoneNumber;
    private SubscriptionPlan plan;

    private Student(UserId userId, PhoneNumber phoneNumber, SubscriptionPlan plan) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.plan = plan;
    }

    public static Student createStudent(String userId, String phoneNumber, SubscriptionPlan plan) {
        UserId validatedUserId = new UserId(userId);
        PhoneNumber validatedPhoneNumber = new PhoneNumber(phoneNumber);
        return new Student(validatedUserId, validatedPhoneNumber, plan);
    }

    public void enrollInCourse(String courseId) {
        if (enrolledCourses.contains(courseId)) {
            throw new AlreadyEnrolledInCourseException(courseId);
        }

        if (enrolledCourses.size() >= this.plan.getMaxCourses()) {
            throw new EnrollmentLimitReachedException(this.plan);
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
