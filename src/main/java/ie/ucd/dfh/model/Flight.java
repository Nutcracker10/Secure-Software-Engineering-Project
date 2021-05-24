package ie.ucd.dfh.model;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import java.util.Calendar;
import java.util.Set;

@Entity
@Indexed
@Table(name = "flight")
public class Flight {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

 //   @NotBlank
    @Field
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="departure")
    private Calendar departure;

 //   @NotBlank
    @Field
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    @Column(name="capacity")
    private int capacity;

    @Min(0)
    @Column(name="price")
    private double price;

    @OneToMany(mappedBy = "flight")
    private Set<Reservation> reservations;

    public Flight(){}

    public Flight(@NotBlank Long id, Calendar departure, Calendar arrival, @NotBlank String dep_airport, @NotBlank String arr_airport, double price) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
        this.dep_airport = dep_airport;
        this.arr_airport = arr_airport;
        this.price = price;
    }

    public Flight(Calendar departure, Calendar arrival, @NotBlank String dep_airport, @NotBlank String arr_airport, double price) {
        this.departure = departure;
        this.arrival = arrival;
        this.dep_airport = dep_airport;
        this.arr_airport = arr_airport;
        this.price = price;
    }

    public Flight(Calendar departure, Calendar arrival, @NotBlank String dep_airport, @NotBlank String arr_airport, double price, int capacity) {
        this.departure = departure;
        this.arrival = arrival;
        this.dep_airport = dep_airport;
        this.arr_airport = arr_airport;
        this.price = price;
        this.capacity = capacity;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getDeparture() {
        return departure;
    }

    public void setDeparture(Calendar departure) {
        this.departure = departure;
    }

    public Calendar getArrival() {
        return arrival;
    }

    public void setArrival(Calendar arrival) {
        this.arrival = arrival;
    }

    public String getDep_airport() {
        return dep_airport;
    }

    public void setDep_airport(String dep_airport) {
        this.dep_airport = dep_airport;
    }

    public String getArr_airport() {
        return arr_airport;
    }

    public void setArr_airport(String arr_airport) {
        this.arr_airport = arr_airport;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}