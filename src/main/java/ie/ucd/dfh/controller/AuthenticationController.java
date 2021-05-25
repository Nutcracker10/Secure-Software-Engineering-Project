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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "locked", required = false) String locked,
                        @RequestParam(value = "blocked", required = false) String blocked,
                        Model model) {
        if(error != null ){
            model.addAttribute("error", "Invalid Username or Password!");
        }
        if(locked != null){
            model.addAttribute("error", "To many invalid attempts. Account locked. Please email us to get it unlocked!");
        }
        if(blocked != null){
            model.addAttribute("error", "IP Address blocked as too many unsuccessful attempts. Please wait 20 minutes and try again!");
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
        if(bindingResult.hasErrors()){
            return "registration";
        }
        userService.save(user);
        log.info("User with ID "+user.getId()+" has registered.");

        return "redirect:/login";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/change-password")
    public String changePassword(Principal principal, String currentPassword, String newPassword, String confirmPassword, RedirectAttributes redirectAttributes){
        User user = userService.findByUsername(principal.getName());
        if(encoder.matches(currentPassword, user.getPassword()) && userValidator.isValidPassword(newPassword) && confirmPassword.equals(newPassword)){
            user.setPassword(encoder.encode(confirmPassword));
            userService.save(user);
            log.info("Password successfully changed for user with ID "+user.getId());
        }else{
            redirectAttributes.addFlashAttribute("error", "Changing password unsuccessful. Please try again.");
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
}
