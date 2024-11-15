package us.cloud.teachme.studentservice.web.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetStudentByIdRequestDto {

    private String studentId;
}
