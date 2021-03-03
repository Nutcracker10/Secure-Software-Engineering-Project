package ie.ucd.dfh.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ie.ucd.dfh.UserSession;
import ie.ucd.dfh.model.Flight;
import ie.ucd.dfh.model.User;
import ie.ucd.dfh.model.Reservation;
import ie.ucd.dfh.repository.FlightRepository;
import ie.ucd.dfh.repository.ReservationRepository;

@Controller
public class ReservationController {
 
    @Autowired
    private UserSession userSession;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private FlightRepository flightrepository;

    @RequestMapping(value="/book-flight")
    public String bookFlight(@RequestParam ("flightId") String flightId) {
        User user = userSession.getUser();

        Long id =  Long.valueOf(flightId);

        Optional<Flight> flight = flightrepository.findById(id);

        if (flight.isPresent()) {
            reservationRepository.save(new Reservation(user, flight.get()));
        }

        return "#";
    }

}
