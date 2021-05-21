package ie.ucd.dfh.controller;

import ie.ucd.dfh.model.*;
import ie.ucd.dfh.repository.ReservationRepository;
import ie.ucd.dfh.repository.UserRepository;
import ie.ucd.dfh.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("#username == authentication.name or hasAuthority('ADMIN')")
    @GetMapping("/profile/{username}")
    public String profile(@PathVariable String username, Model model) {
        User user = userService.findByUsername(username);

        if(user != null){
            model.addAttribute("firstName", user.getFirstName());
            model.addAttribute("lastName", user.getLastName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("address", user.getAddress());
            model.addAttribute("phoneNumber", user.getPhoneNumber());
            model.addAttribute("creditCards", user.getCreditCards());
            model.addAttribute("user", user);
        }else{
            return "redirect:/";
        }

        return "user_profile";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("/edit-profile")
    public void editProfile(Principal principal, String firstName, String lastName, String phoneNumber, String address, String email, HttpServletResponse response) throws IOException{
        User user = userService.findByUsername(principal.getName());
        if(user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setAddress(address);

            userRepository.save(user);

            response.sendRedirect("/profile/"+user.getUsername());
        }
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
