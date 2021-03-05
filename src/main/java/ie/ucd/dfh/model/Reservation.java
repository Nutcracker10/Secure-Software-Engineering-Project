package ie.ucd.dfh.model;
import javax.persistence.*;
import java.util.Calendar;

@Entity
public class Reservation {
    
    @Column(name="reservation_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne    
    @JoinColumn(name="flightId", nullable = false)
    private Flight flight;

    @ManyToOne
    private User user;

    public Reservation(Status status, Flight flight, User user) {
        this.status = status;
        this.flight = flight;
        this.user = user;
    }

    public  Reservation() {}

    public Long getReservationId() {
        return reservationId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void cancelReservation() {
        setStatus(Status.CANCELLED);
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

}
