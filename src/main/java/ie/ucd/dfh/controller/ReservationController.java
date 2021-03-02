package ie.ucd.dfh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ie.ucd.dfh.UserSession;
import ie.ucd.dfh.model.Flight;
import ie.ucd.dfh.model.User;
import ie.ucd.dfh.model.Reservation;
import ie.ucd.dfh.repository.ReservationRepository;

@Controller
public class ReservationController {
 
    @Autowired
    private UserSession userSession;

    @Autowired
    private ReservationRepository reservationRepository;

    @RequestMapping(value={"/book-flight/{flight}"}, method=RequestMethod.POST)
    public void bookFlight(@PathVariable ("flight") Flight flight, Model model) {
        User user = userSession.getUser();
        Reservation reservation = new Reservation(user, flight);


        reservationRepository.save(reservation);
        //TODO gather flight details from form and enter into reservation obj
    }

}
