package ie.ucd.dfh.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private long reservationid;
    private User reserver;
    private String flightCode;
    private Calendar dayOfFlight;

    public Reservation(User reserver, String flightCode, Calendar dayOfFlight) {
        this.reserver = reserver;
        this.flightCode = flightCode;
        this.dayOfFlight = dayOfFlight;
    }

    public String getFlightCode(){
        return flightCode;
    }

    public Calendar getDayOfFlight(){
        return dayOfFlight;
    }
}
