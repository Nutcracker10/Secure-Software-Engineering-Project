package ie.ucd.dfh.controller;

import ie.ucd.dfh.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {

    @ModelAttribute
    public void addAttribute(Model model){
        model.addAttribute("error",null );
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/")
    public String home() {
        return "index";
    }


}
