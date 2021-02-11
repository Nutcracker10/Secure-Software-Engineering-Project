package ie.ucd.dfh.model;


import javax.persistence.*;
import java.util.Calendar;

@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private long reservationId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private String flightCode;
    private Calendar dayOfFlight;

    public Reservation(User user, String flightCode, Calendar dayOfFlight) {
        this.user = user;
        this.flightCode = flightCode;
        this.dayOfFlight = dayOfFlight;
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public Calendar getDayOfFlight() {
        return dayOfFlight;
    }

    public void setDayOfFlight(Calendar dayOfFlight) {
        this.dayOfFlight = dayOfFlight;
    }
}
