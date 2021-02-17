package ie.ucd.dfh.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "flights")
public class Flight {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    @NotBlank
    @Column(name="departure")
    private Calendar departure;

    @NotBlank
    @Column(name="arrival")
    private Calendar arrival;

    @NotBlank
    @Column(name="depAirport")
    private String depAirport;

    @NotBlank
    @Column(name="arrAirpot")
    private String arrAirport;

    @NotBlank
    @Column(name="price")
    private double price;

    public Flight(@NotBlank Long flightId,  @NotBlank Calendar departure, @NotBlank Calendar arrival, @NotBlank String depAirport, @NotBlank String arrAirport,  double price) {
        this.flightId = flightId;
        this.departure = departure;
        this.arrival = arrival;
        this.depAirport = depAirport;
        this.arrAirport = arrAirport;
        this.price = price;
    }

    public void setFlightId(Long flightId) { this.flightId = flightId; }

    public Long getFlightId() {return this.flightId; }

    public void setDeparture(Calendar departure) { this.departure = departure; }

    public Calendar getDeparture() { return this.departure; }

    public void setArrival(Calendar arrival) { this.arrival = arrival;}

    public Calendar getArrival() { return this.arrival; }

    public void setDepAirport(String depAirport) { this.depAirport = depAirport; }

    public String getDepAirport() { return this.depAirport;}

    public void setArrAirport(String arrAirport) {this.arrAirport = arrAirport; }

    public String getArrAirport() { return this.arrAirport; } 

    public void setPrice(double price) { this.price = price; }

    public double getPrice() { return this.price;}
}