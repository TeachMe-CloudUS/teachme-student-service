package us.cloud.teachme.studentservice.infrastructure.persistance.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;
import us.cloud.teachme.studentservice.domain.model.PhoneNumber;

@ReadingConverter
public class PhoneNumberReadingConverter implements Converter<String, PhoneNumber> {

    @Override
    public PhoneNumber convert(@NonNull String source) {
        return new PhoneNumber(source);
    }
}
