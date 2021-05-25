package ie.ucd.dfh.controller;

import ie.ucd.dfh.model.User;
import ie.ucd.dfh.model.wrapper.GuestBookFlight;
import ie.ucd.dfh.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ie.ucd.dfh.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import ie.ucd.dfh.repository.FlightRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

@Controller
public class FlightController {

    private static final Logger log = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserService userService;

    @PreAuthorize("permitAll()")
    @GetMapping("/show-all-flights")
    public String showAllFlights(Model model) {
        model.addAttribute("guestBookFlight", new GuestBookFlight());
        model.addAttribute("flights", flightRepository.findAll() );
        model.addAttribute("flight",new Flight());

        return "search_flights_results.html";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add-flight")
    public void addFlight(String departure_date, String departure_time, String arrival_date,String arrival_time,
                          String dep_airport, String arr_airport, Double price, Integer capacity,  HttpServletResponse response) throws ParseException, IOException {

        Calendar[] departureAndArrival = stringsToDates(departure_date,departure_time,arrival_date,arrival_time);


        Flight flight = new Flight(departureAndArrival[0],departureAndArrival[1],dep_airport,arr_airport, price,capacity);

        flightRepository.save(flight);

        response.sendRedirect("/show-all-flights");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/edit-flight")
    public String editFlight(Principal principal, @ModelAttribute("flight") Flight flight, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                           String departure, String arrival, String departure_time, String arrival_time) throws ParseException, IOException {
        Flight exsistingFlight = flightRepository.findFlightById(flight.getId()).orElse(null);
        User user = userService.findByUsername(principal.getName());

        Calendar[] departureAndArrival = stringsToDates(departure,departure_time,arrival,arrival_time);
        if(exsistingFlight != null){
            exsistingFlight.setArr_airport(flight.getArr_airport());
            exsistingFlight.setArrival(departureAndArrival[1]);
            exsistingFlight.setCapacity(flight.getCapacity());
            exsistingFlight.setDep_airport(flight.getDep_airport());
            exsistingFlight.setDeparture(departureAndArrival[0]);
            exsistingFlight.setPrice(flight.getPrice());

            if(bindingResult.hasErrors()){
                redirectAttributes.addFlashAttribute("error", "Modification of Flight was unsuccessful! Make sure you enter all details correctly!");
                return "redirect:/show-all-flights";
            }

            flightRepository.save(exsistingFlight);
            log.info(String.format("Flight Modified: [User ID: %s, username: %s, Flight ID: %s]",
                    user.getId(), principal.getName(), exsistingFlight.getId()));
        }
        return "redirect:/show-all-flights";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete-flight/{id}")
    public String deleteFlight(@PathVariable Long id, Principal principal,
                             RedirectAttributes redirectAttributes){
        Flight exsistingFlight = flightRepository.findFlightById(id).orElse(null);
        User user = userService.findByUsername(principal.getName());

        if(exsistingFlight == null){
            redirectAttributes.addFlashAttribute("error", "Deletion of Flight was unsuccessful!");
            return "redirect:/show-all-flights";
        }

        if(exsistingFlight != null){
            flightRepository.delete(exsistingFlight);
            log.info(String.format("Flight Deleted: [User ID: %s, username: %s, Flight ID: %s]",
                    user.getId(), principal.getName(), exsistingFlight.getId()));
        }
        return "redirect:/show-all-flights";
    }

    private Calendar[] stringsToDates(String departure_date, String departure_time, String arrival_date,
                                      String arrival_time) throws ParseException {

        String depDateTime = departure_date + " " + departure_time;
        String arrDateTime = arrival_date + " " + arrival_time;

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        dateFormatter.setTimeZone(TimeZone.getDefault());

        Calendar dep_cal = Calendar.getInstance();
        Calendar arr_cal = Calendar.getInstance();
        dep_cal.setTime(dateFormatter.parse(depDateTime));
        arr_cal.setTime(dateFormatter.parse(arrDateTime));

        return new Calendar[]{dep_cal, arr_cal};
    }
}
