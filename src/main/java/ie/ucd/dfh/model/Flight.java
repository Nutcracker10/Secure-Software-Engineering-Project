package ie.ucd.dfh.model;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import java.util.Calendar;
import java.util.Set;

@Entity
@Indexed
@Table(name = "flights")
public class Flight {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public void setId(Long id) { this.id = id; }

    public Long getId() {return this.id; }

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

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
}