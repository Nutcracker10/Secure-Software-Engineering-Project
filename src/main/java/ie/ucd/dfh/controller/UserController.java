package ie.ucd.dfh.controller;

import ie.ucd.dfh.model.*;
import ie.ucd.dfh.repository.ReservationRepository;
import ie.ucd.dfh.repository.UserRepository;
import ie.ucd.dfh.service.UserService;
import ie.ucd.dfh.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

    @PreAuthorize("#username == authentication.name or hasAuthority('ADMIN')")
    @GetMapping("/profile/{username}")
    public String profile(@PathVariable String username, Model model) {
        User user = userService.findByUsername(username);
        if(user != null){
            model.addAttribute("user", user);
            return "user_profile";
        }
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("/edit-profile")
    public String editProfile(Principal principal, @ModelAttribute("user") User user, BindingResult bindingResult) {
        User existingUser = userService.findByUsername(principal.getName());
        if(existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setAddress(user.getAddress());

            if(!user.getEmail().equals(existingUser.getEmail()))
                userValidator.validateEmail(user.getEmail(), bindingResult);

            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());

            userValidator.validate(existingUser, bindingResult);
            if (bindingResult.hasErrors()){
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


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long id, Principal principal, HttpServletResponse response) throws IOException{
        User user = userService.findByUsername(principal.getName());
        if(user != null ){
            User copyUser = new User();
            copyUser.setFirstName(user.getFirstName());
            copyUser.setLastName(user.getLastName());
            copyUser.setUsername(user.getUsername());
            copyUser.setAddress(user.getAddress());
            copyUser.setPhoneNumber(user.getPhoneNumber());
            copyUser.setEmail(user.getEmail());
            userRepository.save(copyUser);
            for(Reservation reservation : user.getReservations()){
                reservation.setUser(copyUser);
            }
            SecurityContextHolder.getContext().setAuthentication(null);
            userRepository.delete(user);
            log.info("User [ID:"+user.getId()+"] has been deleted.");
        }else{
            log.warn("Unauthorized user attempted to delete user [ID:"+id+"]");
        }

        response.sendRedirect("/");
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
    public String cancelReservations(Model model, String cardType, String cardNumber,
                                     String expiryMonth, String expiryYear, String securityCode, @RequestParam Long id) {
        Reservation res = reservationRepository.findById(id).orElse(null);
        Set<CreditCard> cards = res.getUser().getCreditCards();
        System.out.println(cards.size());
        for(CreditCard card : cards){
            if( card.checkIfDetailsMatch(cardType,cardNumber,expiryMonth,expiryYear,securityCode)){
                reservationRepository.delete(res);
            }
        }
        return "index";
    }



}
