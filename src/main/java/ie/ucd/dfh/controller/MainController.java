package ie.ucd.dfh.controller;

import ie.ucd.dfh.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {

    @Autowired private ReservationRepository reservationRepository;

    @ModelAttribute
    public void addAttribute(Model model){
        model.addAttribute("error",null );
/*        if(userSession.isLoginFailed()) model.addAttribute("error","Incorrect email or password." );
        if(userSession.isEmailTaken()) model.addAttribute("emailTaken", "This email is not available.");*/
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

/*    @GetMapping("/login")
    public String login(){
        userSession.setEmailTaken(false);
        return "login";
    }*/


}
