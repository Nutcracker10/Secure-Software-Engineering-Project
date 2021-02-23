package ie.ucd.dfh.model;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import java.util.Calendar;

@Entity
@Indexed
@Table(name = "flights")
public class Flight {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

 //   @NotBlank
    @Field
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="departure")
    private Calendar departure;

 //   @NotBlank
    @Field
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="arrival")
    private Calendar arrival;

    @NotBlank
    @Field
    @Column(name="dep_airport")
    private String dep_airport;

    @NotBlank
    @Field
    @Column(name="arr_airport")
    private String arr_airport;

    @Min(0)
    @Column(name="price")
    private double price;

    public Flight(){}

    public Flight(@NotBlank Long flightId, Calendar departure, Calendar arrival, @NotBlank String dep_airport, @NotBlank String arr_airport, double price) {
        this.flightId = flightId;
        this.departure = departure;
        this.arrival = arrival;
        this.dep_airport = dep_airport;
        this.arr_airport = arr_airport;
        this.price = price;
    }

    public void setFlightId(Long flightId) { this.flightId = flightId; }

    public Long getFlightId() {return this.flightId; }

    public void setDeparture(Calendar departure) { this.departure = departure; }

    public Calendar getDeparture() { return this.departure; }

    public void setArrival(Calendar arrival) { this.arrival = arrival;}

    public Calendar getArrival() { return this.arrival; }

    public void setDep_airport(String dep_airport) { this.dep_airport = dep_airport; }

    public String getDep_airport() { return this.dep_airport;}

    public void setArr_airport(String arr_airport) {this.arr_airport = arr_airport; }

    public String getArr_airport() { return this.arr_airport; }

    public void setPrice(double price) { this.price = price; }

    public double getPrice() { return this.price;}
}