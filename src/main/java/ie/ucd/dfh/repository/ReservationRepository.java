package ie.ucd.dfh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ie.ucd.dfh.model.Flight;
import ie.ucd.dfh.model.Reservation;
import ie.ucd.dfh.model.User;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    //public void insertIntoReservations(User user, Flight flight);

}
