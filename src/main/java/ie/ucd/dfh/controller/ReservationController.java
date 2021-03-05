package ie.ucd.dfh.controller;

import java.io.IOException;
import java.util.Optional;

import ie.ucd.dfh.model.*;
import ie.ucd.dfh.repository.CreditCardRepository;
import ie.ucd.dfh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ie.ucd.dfh.UserSession;
import ie.ucd.dfh.repository.FlightRepository;
import ie.ucd.dfh.repository.ReservationRepository;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ReservationController {
 
    @Autowired
    private UserSession userSession;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private FlightRepository flightrepository;

    @PostMapping(value="/book-flight")
    public void bookFlight(Long flightId, String firstName, String lastName, String homeAddress, String phonenumber, String email,
                           String cardType, String cardNumber, String expiryMonth, String expiryYear, String securityCode, HttpServletResponse response) throws IOException {

        Optional<Flight> flight = flightrepository.findFlightById(flightId);

        // if user is guest, create record of guest and payment
        if (userSession.getUser() == null) {
            CreditCard creditCard = new CreditCard(cardType, cardNumber, expiryMonth, expiryYear, securityCode);
            creditCardRepository.save(creditCard);
        }
        
        if (flight.isPresent()) {
            Reservation reservation = new Reservation(flight.get(), firstName, lastName, homeAddress, phonenumber, email);
            reservation.setStatus(Status.SCHEDULED);
            reservationRepository.save(reservation);

            response.sendRedirect("/");
        }
    }

}
