package ie.ucd.dfh.controller;

import ie.ucd.dfh.model.User;
import ie.ucd.dfh.service.SecurityService;
import ie.ucd.dfh.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping( "/registration")
    public String addUser(String firstName, String surname, String username, String address, String phoneNumber, String email, String password) {
        if(userService.findByUsername(username) != null || userService.findByEmail(email) != null){
            log.info("Could not register User. Username or email already exists.");
            return "redirect:/registration";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);
        user.setFirstName(firstName);
        user.setLastName(surname);
        //user.setRoles(new HashSet<>(roleRepository.findByName()));
        userService.save(user);
        log.info("User with ID "+user.getId()+" has registered.");

        //TODO NOT REACHING AFTER LOGIN FOR SOME REASON
        securityService.authenticate(user.getUsername(), user.getPassword());

        return "redirect:/profile?id="+user.getId();
    }

    @PostMapping("/change-password")
    public String changePassword(Principal principal, String currentPassword, String newPassword, String confirmPassword){
        User user = userService.findByUsername(principal.getName());
        if(encoder.matches(currentPassword, user.getPassword()) && confirmPassword.equals(newPassword)){
            user.setPassword(encoder.encode(confirmPassword));
            userService.save(user);
            log.info("Password successfully changed for user with ID "+user.getId());
        }
        return "redirect:/profile?id="+user.getId();
    }

    @PostMapping ("/logout")
    public String logout(){
        return "redirect:/";
    }

    private boolean strongPass(String password) {
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
    }


}
