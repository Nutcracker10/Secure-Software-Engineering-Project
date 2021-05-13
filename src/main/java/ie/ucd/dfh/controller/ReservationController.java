package ie.ucd.dfh.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import ie.ucd.dfh.model.*;
import ie.ucd.dfh.repository.CreditCardRepository;
import ie.ucd.dfh.repository.UserRepository;
import ie.ucd.dfh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ie.ucd.dfh.repository.FlightRepository;
import ie.ucd.dfh.repository.ReservationRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ReservationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private FlightRepository flightrepository;

    @PostMapping(value="/book-flight")
    public String bookFlight(Long flightId, String firstName, String lastName, String homeAddress, String phonenumber, String email,
                           String cardType, String cardNumber, String expiryMonth, String expiryYear, String securityCode,
                           HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {

        Optional<Flight> flight = flightrepository.findFlightById(flightId);
        User user;
        Reservation reservation;


        if (flight.isPresent()) {
            // if user is guest, create record of guest and payment

            user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAddress(homeAddress);
            user.setPhoneNumber(phonenumber);
            user.setEmail(email);
            CreditCard creditCard = new CreditCard(cardType, cardNumber, expiryMonth, expiryYear, securityCode, user);
            userRepository.save(user);
            creditCardRepository.save(creditCard);
            reservation = new Reservation(Status.SCHEDULED, flight.get(), user);
            reservationRepository.save(reservation);
            redirectAttributes.addFlashAttribute("message",reservation.getReservationId());
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("/show-passengers")
    public void getPassengers(@RequestParam Long id, Model model){
        Reservation reservation = reservationRepository.findById(id).orElse(null);
    }

    @RequestMapping(value = "/member-book-flight")
    public void memberBookFlight(@RequestParam("flightId") Long flightId, Principal principal, HttpServletResponse response) throws IOException{
        Reservation reservation;
        Optional<Flight> flight = flightrepository.findFlightById(flightId);
        User user = userService.findByUsername(principal.getName());

        if (flight.isPresent()) {
            reservation = new Reservation(Status.SCHEDULED, flight.get(), user);
            reservationRepository.save(reservation);
        }
        response.sendRedirect("/");
    }

}
