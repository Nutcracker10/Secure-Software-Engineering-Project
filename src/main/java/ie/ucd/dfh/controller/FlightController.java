package ie.ucd.dfh.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ie.ucd.dfh.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ie.ucd.dfh.repository.FlightRepository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Calendar;

@Controller
public class FlightController {

    private static final Logger log = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightRepository flightRepository;

    @GetMapping("/show-all-flights")
    public String showAllFlights(Model model) {
        model.addAttribute("flights", flightRepository.findAll() );
        return "search_flights_results.html";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add-flight")
    public void addFlight(Calendar departure, Calendar arrival, String dep_airport, String arr_airport, double price){
        Flight flight = new Flight(departure,arrival,dep_airport,arr_airport, price);

        flightRepository.save(flight);
    }
}
