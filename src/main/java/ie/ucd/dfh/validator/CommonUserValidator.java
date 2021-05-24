package ie.ucd.dfh.validator;

import ie.ucd.dfh.model.User;
import ie.ucd.dfh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CommonUserValidator implements Validator {
    private final String NAME_REGEX = "([A-Z][a-z]*).{2,32}";
    private final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private final String PHONE_NUMBER_PATTERN = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";
    private final String ADDRESS_PATTERN = "^[#.0-9a-zA-Z\\s,-]+$";

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    /**
     * Validation common to all Users such as guests and registering users
     */
    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if(!isValid(user.getFirstName(), NAME_REGEX))
            errors.rejectValue("firstName", "InvalidFirstName");
        if(!isValid(user.getLastName(), NAME_REGEX))
            errors.rejectValue("lastName","InvalidLastName");
        if(!isValid(user.getEmail(), EMAIL_PATTERN))
            errors.rejectValue("email", "InvalidEmail");
        if(!isValid(user.getPhoneNumber(), PHONE_NUMBER_PATTERN))
            errors.rejectValue("phoneNumber", "InvalidPhoneNumber");
        if(!isValid(user.getAddress(), ADDRESS_PATTERN))
            errors.rejectValue("address", "InvalidAddress");
    }

    protected boolean isValid(String toValidate, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(toValidate);
        return matcher.matches();
    }
}
