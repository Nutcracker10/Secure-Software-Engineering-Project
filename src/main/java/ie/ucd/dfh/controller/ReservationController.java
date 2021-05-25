package ie.ucd.dfh.controller;


import java.security.Principal;
import java.util.Optional;

import ie.ucd.dfh.model.*;
import ie.ucd.dfh.model.wrapper.GuestBookFlight;
import ie.ucd.dfh.repository.CreditCardRepository;
import ie.ucd.dfh.repository.UserRepository;
import ie.ucd.dfh.service.UserService;
import ie.ucd.dfh.validator.CreditCardValidator;
import ie.ucd.dfh.validator.GuestValidator;
import ie.ucd.dfh.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ie.ucd.dfh.repository.FlightRepository;
import ie.ucd.dfh.repository.ReservationRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

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

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private GuestValidator guestValidator;

    @Autowired
    private CreditCardValidator creditCardValidator;

    /**
     * Book flight as a guest
     */
    @PreAuthorize("permitAll()")
    @PostMapping(value="/book-flight")
    public String bookFlight(@ModelAttribute("guestBookFlight") GuestBookFlight guestBookFlight, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Optional<Flight> flight = flightrepository.findFlightById(guestBookFlight.getFlightId());

        if (flight.isPresent()) {
            User user = guestBookFlight.getUser();
            guestValidator.validate(user,bindingResult);

            CreditCard creditCard = guestBookFlight.getCreditCard();
            creditCard.setUser(user);
            creditCardValidator.validate(creditCard, bindingResult);

            if(bindingResult.hasErrors()){
                redirectAttributes.addFlashAttribute("error", "Booking was unsuccessful! Make sure you enter all details correctly!");
                return "redirect:/show-all-flights";
            }

            userRepository.save(user);
            creditCardRepository.save(creditCard);
            Reservation reservation = new Reservation(Status.SCHEDULED, flight.get(), user);
            reservationRepository.save(reservation);
            redirectAttributes.addFlashAttribute("message",reservation.getReservationId());
        }
        return "redirect:/";
    }

    @GetMapping("/show-passengers")
    public void getPassengers(@RequestParam Long id, Model model){
        Reservation reservation = reservationRepository.findById(id).orElse(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value="/edit-reservation")
    public String editReservation(@ModelAttribute("reservation") Reservation reservation, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes, Principal principal,String status, String flight_id, String email){
       Reservation exsistingRes = reservationRepository.findById(reservation.getReservationId()).orElse(null);
        Flight flight = flightrepository.findFlightById(Long.parseLong(flight_id)).orElse(null);
        User user = userRepository.findByEmail(email).orElse(null);
        User editor = userService.findByUsername(principal.getName());
       if(exsistingRes != null && flight != null && user != null){
           exsistingRes.setStatus(Status.valueOf(status));
           exsistingRes.setUser(user);
           exsistingRes.setFlight(flight);

           if (bindingResult.hasErrors()){
               redirectAttributes.addFlashAttribute("error", "Information Invalid! Make sure you enter all details correctly!");
               return "redirect:/";
           }

           reservationRepository.save(exsistingRes);
           log.info(String.format("Reservation Edited: [User ID: %s, username: %s, Reservation ID: %s]",
                   editor.getId(), principal.getName(), exsistingRes.getReservationId()));
       }
       return "redirect:/";
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/member-book-flight")
    public String memberBookFlight(@RequestParam("flightId") Long flightId, Principal principal){
        Reservation reservation;
        Optional<Flight> flight = flightrepository.findFlightById(flightId);
        User user = userService.findByUsername(principal.getName());

        if (flight.isPresent()) {
            reservation = new Reservation(Status.SCHEDULED, flight.get(), user);
            reservationRepository.save(reservation);
            log.info(String.format("Flight Booked: [User ID: %s, username: %s, Flight ID: %s, Reservation ID: %s]",
                    user.getId(), principal.getName(), flight.get().getId(), reservation.getReservationId()));
        }
        return "redirect:/";
    }
}