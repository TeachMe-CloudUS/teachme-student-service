package us.cloud.teachme.studentservice.application.command;

public record CreateStudentCommand(String userId, String phoneNumber, String plan) {

}