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
public class UserValidator extends CommonUserValidator{
    private final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{5,32}$";


    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        super.validate(user, errors);

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

    private boolean isUserValid(String username){
        return username.matches(USERNAME_PATTERN);
    }
}
