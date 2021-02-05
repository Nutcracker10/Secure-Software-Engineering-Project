package ie.ucd.dfh.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private long reservationid;
    private String nameOfReserver;
    private String flightCode;
    private Date dayOfFlight;

    public Reservation(String nameOfReserver, String flightCode, Date dayOfFlight) {
        this.nameOfReserver = nameOfReserver;
        this.flightCode = flightCode;
        this.dayOfFlight = dayOfFlight;
    }


}
