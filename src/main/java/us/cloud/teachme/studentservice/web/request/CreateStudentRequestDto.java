package us.cloud.teachme.studentservice.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateStudentRequestDto {

    private String userId;

    private String phoneNumber;

    private String plan;
}
