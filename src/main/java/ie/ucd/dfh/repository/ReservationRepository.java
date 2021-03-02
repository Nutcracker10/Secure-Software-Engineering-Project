package ie.ucd.dfh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.ucd.dfh.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
}
