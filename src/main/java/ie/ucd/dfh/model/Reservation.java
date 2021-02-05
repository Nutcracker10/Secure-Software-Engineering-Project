package ie.ucd.dfh.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.util.Date;

@Entity
public class Reservation {
    @GeneratedValue
    private long reservationid;
    private String nameOfReserver;
    private String flightCode;
    private Date dayOfFlight;

    public Reservation(String nameOfReserver, String flightCode, Date dayOfFlight){
        this.nameOfReserver = nameOfReserver;
        this.flightCode = flightCode;
        this.dayOfFlight = dayOfFlight;
    }
}
