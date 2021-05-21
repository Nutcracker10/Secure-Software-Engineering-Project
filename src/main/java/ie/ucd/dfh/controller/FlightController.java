package ie.ucd.dfh.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ie.ucd.dfh.repository.FlightRepository;

@Controller
public class FlightController {

    private static final Logger log = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightRepository flightRepository;

    @PreAuthorize("permitAll()")
    @GetMapping("/show-all-flights")
    public String showAllFlights(Model model) {
        model.addAttribute("flights", flightRepository.findAll() );
        return "search_flights_results.html";
    }
}
