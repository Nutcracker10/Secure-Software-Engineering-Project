package ie.ucd.dfh.controller;

import ie.ucd.dfh.UserSession;
import ie.ucd.dfh.model.Credentials;
import ie.ucd.dfh.model.User;
import ie.ucd.dfh.repository.CredentialsRepository;
import ie.ucd.dfh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class AuthenticationController {

    @Autowired
    private UserSession userSession;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/process-login")
    public void processLogin(Model model, String email, String password, HttpServletResponse response) throws IOException{
        User user = userRepository.findUserByEmail(email).orElse(null);
        boolean success = user != null && user.getCredentials() != null && encoder.matches(password, user.getCredentials().getPassword());
        if (success){
            userSession.setUser(user);
            userSession.setLoginFailed(false);
            response.sendRedirect("/");
        }else{
            userSession.setLoginFailed(true);
            response.sendRedirect("/login");
        }
    }

    @PostMapping("/process-registration")
    public void processRegistration(String firstName, String surname, String address, String phoneNumber, String email, String password, HttpServletResponse response) throws IOException {
        User user = userRepository.findUserByEmail(email).orElse(null);
        if(user != null) {
            userSession.setEmailTaken(true);
            response.sendRedirect("/login");
        }
        else{
            userSession.setEmailTaken(false);
            String encodedPassword = encoder.encode(password);
            Credentials credentials = new Credentials(encodedPassword);
            user = new User(firstName, surname, address, phoneNumber, email, "member");
            user.setCredentials(credentials);
            credentialsRepository.save(credentials);
            userRepository.save(user);
            userSession.setUser(user);
            response.sendRedirect("/");
        }

    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException{
        userSession.setUser(null);
        response.sendRedirect("/");
    }

}
