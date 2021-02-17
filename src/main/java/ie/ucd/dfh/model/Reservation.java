package ie.ucd.dfh.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.Calendar;

@Entity
public class Reservation {
    
    @Column(name="reservationId")
    @Id @GeneratedValue
    private long reservationId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name="flight_id")
    @NotBlank
    private Long flightId;

    
    public Reservation(User user, Long flightId) {
        this.user = user;
        this.flightId = flightId;
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

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightCode(Long flightId) {
        this.flightId = flightId;
    }
}
