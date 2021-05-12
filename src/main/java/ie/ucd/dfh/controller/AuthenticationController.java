package ie.ucd.dfh.controller;

import ie.ucd.dfh.UserSession;
import ie.ucd.dfh.model.User;
import ie.ucd.dfh.repository.RoleRepository;
import ie.ucd.dfh.service.SecurityService;
import ie.ucd.dfh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.logging.Logger;

@Controller
public class AuthenticationController {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserSession userSession;

    @Autowired
    private UserService userService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);
        user.setFirstName(firstName);
        user.setLastName(surname);
        user.setRoles(Arrays.asList(roleRepository.findByName("USER").orElse(null)));
        userService.save(user);

        //securityService.autoLogin(user.getUsername(), user.getPassword());

        return "redirect:/";
    }


/*    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        //userValidator.validate(userForm, bindingResult);
        System.out.println("mail " + userForm.getEmail());

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);

        //securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/";
    }*/

/*    @PostMapping("/login")
    public void processLogin(Model model, String email, String password, HttpServletResponse response) throws IOException{
        User user = userService.findByEmail(email);
        boolean success = user != null && encoder.matches(password, user.getPassword());
        if (success){
            logger.info("Login Success");
            userSession.setUser(user);
            userSession.setLoginFailed(false);
            response.sendRedirect("/");
        }else{
            logger.info("Login Unsuccessful");
            userSession.setLoginFailed(true);
            response.sendRedirect("/login");
        }
    }

    @PostMapping("/registration")
    public void processRegistration(String firstName, String surname, String address, String phoneNumber, String email, String password, HttpServletResponse response) throws IOException {
        User user = userService.findByEmail(email);
        if(user != null) {
            logger.info("Registration Unsuccessful");

            userSession.setEmailTaken(true);
            response.sendRedirect("/login");
        }
        else{
            logger.info("Registration Success");
            userSession.setEmailTaken(false);
            String encodedPassword = encoder.encode(password);
            user = new User(firstName, surname, address, phoneNumber, email);
            user.setPassword(encodedPassword);
            userService.save(user);
            securityService.autoLogin(user.getEmail(), user.getPassword());
            userSession.setUser(user);
            response.sendRedirect("/profile?id="+user.getId());
        }

    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException{
        userSession.setUser(null);
        response.sendRedirect("/");
    }*/

}
