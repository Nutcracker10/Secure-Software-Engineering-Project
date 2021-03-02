package ie.ucd.dfh.controller;

import ie.ucd.dfh.UserSession;
import ie.ucd.dfh.model.Flight;
import ie.ucd.dfh.model.HibernateSearchDao;
import ie.ucd.dfh.model.User;
import ie.ucd.dfh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserSession userSession;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HibernateSearchDao searchservice;

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

            model.addAttribute("creditCard", user.getCreditCard());
        }else{
            response.sendRedirect("/");
        }

        return "user_profile";
    }

    @PostMapping("/edit-profile")
    public void editProfile(Model model, String firstName, String lastName, String email, HttpServletResponse response) throws IOException{
        User user = userRepository.findUserById(userSession.getUser().getId()).orElse(null);
        if(user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);

            userRepository.save(user);
            response.sendRedirect("/profile?id="+user.getId());
        }
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
}
