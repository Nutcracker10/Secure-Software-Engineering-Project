package ie.ucd.dfh.controller;

import ie.ucd.dfh.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ie.ucd.dfh.repository.FlightRepository;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class FlightController {
    
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserSession userSession;

    @ModelAttribute
    public void addAttribute(Model model){
        model.addAttribute("user", userSession.getUser());
    }

    @GetMapping("/show-all-flights")
    public String showAllFlights(Model model) {
        model.addAttribute("flights", flightRepository.findAll() );
        return "search_flights_results.html";
    }


}
