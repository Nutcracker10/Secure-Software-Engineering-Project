package ie.ucd.dfh.controller;

import ie.ucd.dfh.UserSession;
import ie.ucd.dfh.model.User;
import ie.ucd.dfh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    private UserSession userSession;

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void addAttribute(Model model){
        model.addAttribute("user", userSession.getUser());
    }

    @GetMapping("/profile")
    public String proifle(@RequestParam("id") Long id, Model model, HttpServletResponse response) throws IOException {
        User user = userSession.getUser();

        if(user != null && user.getUserId().equals(id) && user.getRole().equals("member")){
            model.addAttribute("firstName", user.getFirstName());
            model.addAttribute("lastName", user.getLastName());
            model.addAttribute("email", user.getEmail());
        }else{
            response.sendRedirect("/");
        }

        return "user_profile";
    }

    @PostMapping("/edit-profile")
    public void editProfile(Model model, String firstName, String lastName, String email, HttpServletResponse response) throws IOException{
        User user = userSession.getUser();
        if(user != null){
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            userRepository.save(user);
            response.sendRedirect("/profile?id="+user.getUserId());
        }
    }
}
