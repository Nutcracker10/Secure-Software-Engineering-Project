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
        User user;
        Reservation reservation;


        if (flight.isPresent()) {
            // if user is guest, create record of guest and payment

            user = new User(firstName, lastName, homeAddress, phonenumber, email, "guest");
            CreditCard creditCard = new CreditCard(cardType, cardNumber, expiryMonth, expiryYear, securityCode, user);
            userRepository.save(user);
            creditCardRepository.save(creditCard);

            reservation = new Reservation(flight.get(), user.getFirstName(), user.getLastName(), user.getAddress(), user.getPhoneNumber(), user.getEmail());
            reservation.setStatus(Status.SCHEDULED);
            reservation.setUser(user);
            reservationRepository.save(reservation);
        }


        response.sendRedirect("/");
    }

    @RequestMapping(value = "/member-book-flight")
    public void memberBookFlight(@RequestParam("flightId") Long flightId, HttpServletResponse response) throws IOException{
        Reservation reservation;
        Optional<Flight> flight = flightrepository.findFlightById(flightId);
        User user = userSession.getUser();

        if (flight.isPresent()) {
            reservation = new Reservation(flight.get(), user.getFirstName(), user.getLastName(), user.getAddress(), user.getPhoneNumber(), user.getEmail());
            reservation.setStatus(Status.SCHEDULED);
            reservation.setUser(user);
            reservationRepository.save(reservation);
        }
        response.sendRedirect("/");
    }

}
