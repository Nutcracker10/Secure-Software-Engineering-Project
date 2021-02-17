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
    @JoinColumn(name = "user_id", nullable = false)
    private Long userId;

    @Column(name="flight_id")
    @NotBlank
    private Long flightId;

    
    public Reservation(@NotBlank Long userId, @NotBlank Long flightId) {
        this.userId = userId;
        this.flightId = flightId;
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getUser() {
        return userId;
    }

    public void setUser(Long userId) {
        this.userId = userId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightCode(Long flightId) {
        this.flightId = flightId;
    }
}
