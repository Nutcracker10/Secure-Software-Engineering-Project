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
public class UserValidator implements Validator {
    private final String NAME_REGEX = "([A-Z][a-z]*).{2,32}";
    private final String LAST_NAME_REGEX = "(^[\\p{L}\\s.’\\-,]+$).{2,32}";
    private final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private final String USERNAME_PATTERN = "^[a-z0-9_-]{5,32}$";
    private final String PHONE_NUMBER_PATTERN = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";
    private final String ADDRESS_PATTERN = "^[#.0-9a-zA-Z\\s,-]+$";

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

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
        if ((user.getUsername().length() < 5 || user.getUsername().length() > 32) ||
                (!isUserValid(user.getUsername()))
                //(!isValid(user.getPassword(), PASSWORD_REGEX))
        )
            errors.rejectValue("passwordConfirm", "Diff.user.passwordConfirm");
    }

    public void validateUsernameAndEmail(User user, Errors errors){
        if ( !isValidEmail(user.getEmail()) || !isValidUsername(user.getUsername()))
            errors.rejectValue("passwordConfirm", "Diff.user.passwordConfirm");
    }

    private boolean isValidUsername(String username) {
        return userService.findByUsername(username) == null;
    }

    private boolean isValidEmail(String email) {
        return userService.findByEmail(email) == null;
    }

    public void validateEmail(String email, Errors errors){
        if( !isValidEmail(email) ) errors.rejectValue("passwordConfirm", "Diff.user.passwordConfirm");
    }

    public void validateUsername(String username, Errors errors){
        if(!isValidUsername(username) ) errors.rejectValue("passwordConfirm", "Diff.user.passwordConfirm");
    }

    private boolean isValid(String toValidate, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(toValidate);
        return matcher.matches();
    }
    private boolean isUserValid(String username){
        return username.matches(USERNAME_PATTERN);
    }
}
