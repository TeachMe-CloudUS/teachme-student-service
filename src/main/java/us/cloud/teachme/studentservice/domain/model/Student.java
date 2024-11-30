package us.cloud.teachme.studentservice.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import us.cloud.teachme.studentservice.domain.exception.AlreadyEnrolledInCourseException;
import us.cloud.teachme.studentservice.domain.exception.CourseAlreadyCompletedException;
import us.cloud.teachme.studentservice.domain.exception.EnrollmentLimitReachedException;
import us.cloud.teachme.studentservice.domain.exception.NotEnrolledInCourseException;
import us.cloud.teachme.studentservice.domain.model.valueObject.UserId;

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

    @Indexed(unique = true)
    private UserId userId;

    private ContactInformation contactInformation;
    private ProfileInformation profileInformation;

    private Student(UserId userId, ContactInformation contactInformation, ProfileInformation profileInformation) {
        this.userId = userId;
        this.contactInformation = contactInformation;
        this.profileInformation = profileInformation;
    }

    public static Student createStudent(String userId, ContactInformation contactInformation, ProfileInformation profileInformation) {
        UserId validatedUserId = new UserId(userId);
        return new Student(validatedUserId, contactInformation, profileInformation);
    }

    public void enrollInCourse(String courseId) {
        if (enrolledCourses.contains(courseId)) {
            throw new AlreadyEnrolledInCourseException(courseId);
        }

        if (enrolledCourses.size() >= this.profileInformation.getPlan().getMaxCourses()) {
            throw new EnrollmentLimitReachedException(this.profileInformation.getPlan());
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
