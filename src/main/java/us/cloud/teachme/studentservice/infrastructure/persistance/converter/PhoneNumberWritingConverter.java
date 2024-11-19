package us.cloud.teachme.studentservice.infrastructure.persistance.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import us.cloud.teachme.studentservice.domain.model.PhoneNumber;

@WritingConverter
public class PhoneNumberWritingConverter implements Converter<PhoneNumber, String> {

    @Override
    public String convert(PhoneNumber phoneNumber) {
        return phoneNumber.value();
    }
}
