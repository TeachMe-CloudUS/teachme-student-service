package us.cloud.teachme.studentservice.domain.model;

import lombok.Data;
import us.cloud.teachme.studentservice.domain.model.valueObject.Email;
import us.cloud.teachme.studentservice.domain.model.valueObject.Name;
import us.cloud.teachme.studentservice.domain.model.valueObject.PhoneNumber;

@Data
public class ContactInformation {

    private Name name;
    private Email email;
    private PhoneNumber phoneNumber;
    private String country;

    private ContactInformation(Name name, Email email, PhoneNumber phoneNumber, String country) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
    }

    public static ContactInformation create(String name, String surname, String email, String phoneNumber, String country) {
        Name validatedName = new Name(name, surname);
        Email validatedEmail = new Email(email);
        PhoneNumber validatedPhoneNumber = new PhoneNumber(phoneNumber);
        return new ContactInformation(validatedName, validatedEmail, validatedPhoneNumber, country);
    }
}
