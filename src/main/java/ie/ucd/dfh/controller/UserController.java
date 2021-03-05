package ie.ucd.dfh.controller;

import ie.ucd.dfh.UserSession;
import ie.ucd.dfh.model.*;
import ie.ucd.dfh.repository.ReservationRepository;
import ie.ucd.dfh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserSession userSession;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HibernateSearchDao searchservice;

    @Autowired
    private ReservationRepository reservationRepository;

    @ModelAttribute
    public void addAttribute(Model model){
        model.addAttribute("user", userSession.getUser());
    }

    @GetMapping("/profile")
    public String profile(@RequestParam("id") Long id, Model model, HttpServletResponse response) throws IOException {
        User user = userRepository.findUserById(userSession.getUser().getId()).orElse(null);

        if(user != null && user.getId().equals(id) && user.getRole().equals("member")){
            model.addAttribute("firstName", user.getFirstName());
            model.addAttribute("lastName", user.getLastName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("address", user.getAddress());
            model.addAttribute("phoneNumber", user.getPhoneNumber());
            model.addAttribute("creditCards", user.getCreditCards());
        }else{
            response.sendRedirect("/");
        }

        return "user_profile";
    }

    @PostMapping("/edit-profile")
    public void editProfile(Model model, String firstName, String lastName, String phoneNumber, String address, String email, HttpServletResponse response) throws IOException{
        User user = userRepository.findUserById(userSession.getUser().getId()).orElse(null);
        if(user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setAddress(address);

            userRepository.save(user);
            response.sendRedirect("/profile?id="+user.getId());
        }
    }


    @GetMapping("/history")
    public String displayHistory(@RequestParam("id") Long id, Model model) {
        Optional<User> userResponse = userRepository.findUserById(id);
        User user = userResponse.get();
        Set<Reservation> reservations =  user.getReservations();
        model.addAttribute("reservations", reservations);

        return "history.html";
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


    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long id, HttpServletResponse response) throws IOException{
        User user = userRepository.findUserById(id).orElse(null);
        if(user != null && userSession.getUser() != null && userSession.getUser().getId().equals(id)){
            User copyUser = new User(user.getFirstName(), user.getLastName(), user.getAddress(), user.getPhoneNumber(), user.getEmail(), "guest");
            userRepository.save(copyUser);
            for(Reservation reservation : user.getReservations()){
                reservation.setUser(copyUser);
            }
            userRepository.delete(user);
            userSession.setUser(null);
        }
        response.sendRedirect("/");
    }

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
        System.out.println("WE ARE IN");
        model.addAttribute("resId", reservationId);
        return "modals/delete_reservation_check.html";
    }

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
