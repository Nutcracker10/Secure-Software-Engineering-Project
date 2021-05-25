package ie.ucd.dfh.controller;

import ie.ucd.dfh.model.*;
import ie.ucd.dfh.model.wrapper.GuestBookFlight;
import ie.ucd.dfh.repository.ReservationRepository;
import ie.ucd.dfh.repository.UserRepository;
import ie.ucd.dfh.service.UserService;
import ie.ucd.dfh.validator.CommonUserValidator;
import ie.ucd.dfh.validator.CreditCardValidator;
import ie.ucd.dfh.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HibernateSearchDao searchservice;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private CommonUserValidator commonUserValidator;

    @Autowired
    private CreditCardValidator creditCardValidator;

    @Autowired
    private TextEncryptor textEncryptor;

    @PreAuthorize("#username == authentication.name or hasAuthority('ADMIN')")
    @GetMapping("/profile/{username}")
    public String profile(@PathVariable String username, Model model) {
        User user = userService.findByUsername(username);
        if(user != null){
            model.addAttribute("user", user);
            model.addAttribute("creditCard", new CreditCard());
            return "user_profile";
        }
        return "redirect:/";
    }

    @PreAuthorize("#user.username == authentication.name or hasAuthority('ADMIN')")
    @PostMapping("/edit-profile")
    public String editProfile(Principal principal, @ModelAttribute("user") User user, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        User existingUser = userService.findByUsername(principal.getName());

        if(existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setAddress(user.getAddress());

            if(!user.getEmail().equals(existingUser.getEmail()))
                userValidator.validateEmail(user.getEmail(), bindingResult);

            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());

            commonUserValidator.validate(existingUser, bindingResult);
            if (bindingResult.hasErrors()){
                redirectAttributes.addFlashAttribute("error", "Information Invalid! Make sure you enter all details correctly!");
                return "redirect:/profile/"+existingUser.getUsername();
            }

            userRepository.save(existingUser);
            return "redirect:/profile/"+existingUser.getUsername();
        }
        return "redirect:/";
    }

    @PreAuthorize("#username == authentication.name or hasAuthority('ADMIN')")
    @GetMapping("/history/{username}")
    public String displayHistory(@PathVariable String username, Model model) {
        model.addAttribute("creditCard", new CreditCard());
        User user = userService.findByUsername(username);
        if(user != null){
            Set<Reservation> reservations =  user.getReservations();
            model.addAttribute("reservations", reservations);
            return "history.html";
        }
        return "redirect:/";
    }


    @GetMapping("/search_flights")
    public String displayFlights(@RequestParam(value="search",required = false)String query, Model model){
        model.addAttribute("guestBookFlight", new GuestBookFlight());
        model.addAttribute("flight", new Flight());

        if(query.isEmpty()){
            return "search_flights_results.html";
        }
        List<Flight> searchResults = null;
        try {
            searchResults = searchservice.fuzzySearchFlight(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("flights", searchResults);
        return "search_flights_results.html";
    }


    @PreAuthorize("#username == authentication.name or hasAuthority('ADMIN')")
    @RequestMapping(value = "/user/delete/{username}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable String username, Principal principal, HttpServletRequest request) throws ServletException {
        User user = userService.findByUsername(username);
        if(user != null ){
            User copyUser = new User();
            copyUser.setFirstName(user.getFirstName());
            copyUser.setLastName(user.getLastName());
            copyUser.setAddress(user.getAddress());
            copyUser.setPhoneNumber(user.getPhoneNumber());
            copyUser.setEmail(user.getEmail());
            userRepository.save(copyUser);
            for(Reservation reservation : user.getReservations()){
                reservation.setUser(copyUser);
            }

            request.logout();
            userRepository.delete(user);

            log.info("User [ID:"+user.getId()+"] has been deleted.");
        }else{
            log.warn("Unauthorized user attempted to delete user [ID:"+username+"]");
        }

        return "redirect:/";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/reservation")
    public String getReservation(Model model, @RequestParam Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);

        model.addAttribute("retrievedReservation", reservation);
        if(reservation != null){
            Set<Reservation> passengers = reservation.getFlight().getReservations();
            model.addAttribute("passengers", passengers);
        }
        return "index";
    }


    @GetMapping("/cancel-res-prompt")
    public String promptReservationCancel(@RequestParam("id") Long reservationId, Model model ){
        model.addAttribute("resId", reservationId);
        return "modals/delete_reservation_check.html";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @DeleteMapping("/delete-reservation")
    public String cancelReservations(@ModelAttribute("creditCard") CreditCard creditCard, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam Long id) {
        Reservation res = reservationRepository.findById(id).orElse(null);
        User user = res.getUser();

        if(user != null){
            creditCardValidator.validate(creditCard, bindingResult);
            if(bindingResult.hasErrors()){
                redirectAttributes.addFlashAttribute("error", "Credit Card details incorrect.");
                return "redirect:/history/"+user.getUsername();
            }
            for(CreditCard card : user.getCreditCards()){
                if(creditCardValidator.checkIfDetailsMatch(card, creditCard)){
                    reservationRepository.delete(res);
                    redirectAttributes.addFlashAttribute("error", "Successfully Cancelled.");
                    return "redirect:/history/"+user.getUsername();
                }
            }
            redirectAttributes.addFlashAttribute("error", "Details do not match. Please try again.");
            return "redirect:/history/"+user.getUsername();
        }
        return "redirect:/";
    }
}
