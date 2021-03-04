package ie.ucd.dfh.model;


import org.hibernate.sql.Update;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @JoinColumn(name="id", nullable = false)
    private Flight flight;

    @NotBlank
    @Column(name="first_name")
    private String firstName;

    @NotBlank
    @Column(name="last_name")
    private String lastName;

    @NotBlank
    @Column(name="home_address")
    private String homeAddress;



    @NotNull
    @Column(name="phonenumber")
    private String phonenumber;

    @NotBlank
    @Column(name="email")
    private String email;

    
    public Reservation(@NotBlank Flight flight, @NotBlank String firstName, @NotBlank String lastName, @NotBlank String homeAddress, @NotNull String phonenumber, @NotBlank String email) {
        this.flight = flight;
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeAddress = homeAddress;
        this.phonenumber = phonenumber;
        this.email = email;
    }

    public  Reservation() {}

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public Flight getFlightId() {
        return flight;
    }

    public void setFlightCode(Flight flight) {
        this.flight = flight;
    }

    public String getFirstName() { 
        return this.firstName;
    }

    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHomeAddress() {
        return this.homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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
