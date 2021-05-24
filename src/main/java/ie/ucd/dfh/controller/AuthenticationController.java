package ie.ucd.dfh.controller;

import ie.ucd.dfh.model.ConfirmationToken;
import ie.ucd.dfh.model.User;
import ie.ucd.dfh.service.EmailSenderService;
import ie.ucd.dfh.service.SecurityService;
import ie.ucd.dfh.service.UserService;
import ie.ucd.dfh.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserValidator userValidator;

    @PreAuthorize("permitAll()")
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) Boolean error, Model model) {
        if(error != null && error){
            model.addAttribute("error", "Invalid Username or Password!");
        }
        return "login";
    }


    @PreAuthorize("permitAll()")
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PreAuthorize("permitAll()")
    @PostMapping( "/registration")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        userValidator.validateUsernameAndEmail(user, bindingResult);

        if(bindingResult.hasErrors()){
            return "registration";
        }
        userService.save(user);
        log.info("User with ID "+user.getId()+" has registered.");

        return "redirect:/login";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/change-password")
    public String changePassword(Principal principal, String currentPassword, String newPassword, String confirmPassword){
        User user = userService.findByUsername(principal.getName());
        if(encoder.matches(currentPassword, user.getPassword()) && confirmPassword.equals(newPassword)){
            user.setPassword(encoder.encode(confirmPassword));
            userService.save(user);
            log.info("Password successfully changed for user with ID "+user.getId());
        }
        return "redirect:/profile/"+user.getUsername();
    }

    @PostMapping ("/logout")
    public String logout(){
        return "redirect:/";
    }

    @PostMapping("/forgot-password")
    public String forgotUserPassword(String email, Model model){
/*        User existingUser = userService.findByEmail(email);
        if(existingUser != null){
            ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);
            userService.save(confirmationToken);
            emailSenderService.sendForgotEmail(existingUser.getEmail(), confirmationToken);
        }
        model.addAttribute("message", "Message sent to the email address provided");*/
        return "redirect:/login";
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
