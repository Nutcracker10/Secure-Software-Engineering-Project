package ie.ucd.dfh.model;


import org.hibernate.sql.Update;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.search.annotations.Field;

@Entity
public class Reservation {
    
    @Column(name="reservation_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

<<<<<<< HEAD
=======
    @ManyToOne
    private User user;

>>>>>>> master
    @OneToOne    
    @JoinColumn(name="flightId", nullable = false)
    private Flight flight;

<<<<<<< HEAD
    @NotBlank
    @Column(name="first_name")
    private String firstName;

    @NotBlank
    @Column(name="last_name")
    private String lastName;

    @NotBlank
    @Column(name="home_address")
    private String homeAddress;

    @NotBlank
    @Column(name="phonenumber")
    private int phonenumber;

    @NotBlank
    @Column(name="email")
    private String email;

    
    public Reservation(@NotBlank Flight flight, @NotBlank String firstName, @NotBlank String lastName, @NotBlank String homeAddress, @NotBlank int phonenumber, @NotBlank String email) {
=======

    public Reservation() {
    }

    public Reservation(@NotBlank User user, @NotBlank Flight flight) {
        this.user = user;
>>>>>>> master
        this.flight = flight;
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeAddress = homeAddress;
        this.phonenumber = phonenumber;
        this.email = email;
    }

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

    public int getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
