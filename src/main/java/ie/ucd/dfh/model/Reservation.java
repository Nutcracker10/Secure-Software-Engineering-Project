package ie.ucd.dfh.model;


import org.hibernate.sql.Update;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.Calendar;

@Entity
public class Reservation {
    
    @Column(name="reservation_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private User user;

    @OneToOne    
    @JoinColumn(name="flightId", nullable = false)
    private Flight flight;


    public Reservation() {
    }

    public Reservation(@NotBlank User user, @NotBlank Flight flight, Status status) {
        this.user = user;
        this.flight = flight;
        this.status = status;
    }

    public Status getStatus(){
        //Do checks to see if Status has changed
        Calendar calendar = Calendar.getInstance();
        if(calendar.after(flight.getDeparture())){
            setStatus(Status.PAST);
        }
        return status;
    }
    public void setStatus(Status status){
        this.status = status;
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

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
