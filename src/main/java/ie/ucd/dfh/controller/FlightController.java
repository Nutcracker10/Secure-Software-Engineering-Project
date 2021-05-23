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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

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
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add-flight")
    public void addFlight(String departure_date, String departure_time, String arrival_date,String arrival_time,
                          String dep_airport, String arr_airport, Double price, Integer capacity,  HttpServletResponse response) throws ParseException, IOException {

        Calendar[] departureAndArrival = stringsToDates(departure_date,departure_time,arrival_date,arrival_time);


        Flight flight = new Flight(departureAndArrival[0],departureAndArrival[1],dep_airport,arr_airport, price,capacity);

        flightRepository.save(flight);

        response.sendRedirect("/show-all-flights");
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
