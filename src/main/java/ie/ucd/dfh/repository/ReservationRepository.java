package ie.ucd.dfh.repository;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;

import ie.ucd.dfh.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
=======
import ie.ucd.dfh.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
>>>>>>> e100c6d12ce2a84520fbbdc869cd937181625327
}
