package ie.ucd.dfh.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ie.ucd.dfh.model.Reservation;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {    

}
