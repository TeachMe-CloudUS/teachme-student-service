package us.cloud.teachme.studentservice.infrastructure.persistance.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import us.cloud.teachme.studentservice.domain.model.UserId;

@WritingConverter
public class UserIdWritingConverter implements Converter<UserId, String> {

    @Override
    public String convert(UserId userId) {
        return userId.value();
    }
}
