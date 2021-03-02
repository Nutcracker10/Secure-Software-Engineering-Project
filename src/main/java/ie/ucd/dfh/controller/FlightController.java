package ie.ucd.dfh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ie.ucd.dfh.repository.FlightRepository;

@Controller
public class FlightController {
    
    @Autowired
    private FlightRepository flightRepository;

    @GetMapping("/show-all-flights")
    public String showAllFlights(Model model) {
       
        return "search_flights_results.html";
    }


}
