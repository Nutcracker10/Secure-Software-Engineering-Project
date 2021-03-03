package ie.ucd.dfh.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.Calendar;

@Entity
public class Reservation {
    
    @Column(name="reservation_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @OneToOne    
    @JoinColumn(name="flightId", nullable = false)
    private Flight flight;


    public Reservation() {
    }

    public Reservation(@NotBlank User user, @NotBlank Flight flight) {
        this.user = user;
        this.flight = flight;
    }

    public Long getReservationId() {
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

    public Flight getFlightId() {
        return flight;
    }

    public void setFlightCode(Flight flight) {
        this.flight = flight;
    }
}
