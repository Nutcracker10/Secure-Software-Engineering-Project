package ie.ucd.dfh.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ie.ucd.dfh.model.Flight;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FlightRepository extends JpaRepository<Flight, Long>{
    public List<Flight> findAll();

    public Optional<Flight> findById(Long id);

    public Optional<Flight> findFlightById(Long id);
}
