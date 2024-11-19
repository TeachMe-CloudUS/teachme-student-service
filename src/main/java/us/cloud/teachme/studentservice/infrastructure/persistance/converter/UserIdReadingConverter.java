package us.cloud.teachme.studentservice.infrastructure.persistance.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;
import us.cloud.teachme.studentservice.domain.model.UserId;

@ReadingConverter
public class UserIdReadingConverter implements Converter<String, UserId> {

    @Override
    public UserId convert(@NonNull String source) {
        return new UserId(source);
    }
}
