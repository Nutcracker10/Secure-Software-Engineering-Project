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
    private final String PASSWORD_REGEX = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,32})";

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
                !isUserValid(user.getUsername()) ||
                !isValid(user.getPassword(), PASSWORD_REGEX) ||
                !isValidEmail(user.getEmail()) ||
                !isValidUsername(user.getUsername())
        )
            errors.rejectValue("passwordConfirm", "Diff.user.passwordConfirm");
    }

    public void validatePassword(String password, Errors errors){
        if(isValidPassword(password))
            errors.rejectValue("passwordConfirm", "Diff.user.passwordConfirm");
    }

    public void validateUsernameAndEmail(User user, Errors errors){
        if ( !isValidEmail(user.getEmail()) || !isValidUsername(user.getUsername()))
            errors.rejectValue("passwordConfirm", "Diff.user.passwordConfirm");
    }

    public boolean isValidPassword(String password){
        return isValid(password, PASSWORD_REGEX);
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

    /*    private boolean strongPass(String password) {
        boolean flag = false;
        if ( password.length() < 8) {
            return false;
        }
        //Check for at least one upper case character
        for (int i=0; i<password.length(); i++){
            flag = Character.isUpperCase(password.charAt(i));

            if (flag == true )
                break;
        }
        if (flag != true) {
            return  false;
        }
        flag = false;
        //check for number in password
        for (int i=0; i<password.length(); i++) {
            flag = Character.isDigit(password.charAt(i));

            if ( flag == true )
                break;
        }
        if (flag != true) {
            return false;
        }
        flag = false;
        //check for special char in password
        for (int i=0; i<password.length(); i++ ) {
            flag = (String.valueOf(password.charAt(i)).matches("[^a-zA-Z0-9]") );

            if (flag == true) {
                break;
            }
        }
        return true;
    }*/
}
