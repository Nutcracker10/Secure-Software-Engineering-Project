package ie.ucd.dfh.controller;

import java.io.IOException;
import java.util.Optional;

import ie.ucd.dfh.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ie.ucd.dfh.UserSession;
import ie.ucd.dfh.model.Flight;
import ie.ucd.dfh.model.User;
import ie.ucd.dfh.model.Reservation;
import ie.ucd.dfh.repository.FlightRepository;
import ie.ucd.dfh.repository.ReservationRepository;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ReservationController {
 
    @Autowired
    private UserSession userSession;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private FlightRepository flightrepository;

    @PostMapping(value="/book-flight")
    public void bookFlight(Long flightId, String firstName, String lastName, String homeAddress, String phonenumber, String email, HttpServletResponse response) throws IOException {

        Optional<Flight> flight = flightrepository.findFlightById(flightId);
        
        if (flight.isPresent()) {
            Reservation reservation = new Reservation(flight.get(), firstName, lastName, homeAddress, phonenumber, email);
            reservation.setStatus(Status.SCHEDULED);
            reservationRepository.save(reservation);

            response.sendRedirect("/");
        }
    }

}
