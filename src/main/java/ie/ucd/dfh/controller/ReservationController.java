package ie.ucd.dfh.controller;

;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/book-flight")
    public void bookFlight() {
        User user = userSession.getUser();
        Flight flight = new Flight();
        Reservation reservation = new Reservation(user, flight);

        //TODO gather flight details from form and enter into reservation obj
    }

}
