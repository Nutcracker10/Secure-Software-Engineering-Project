package ie.ucd.dfh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ie.ucd.dfh.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>{
    
    //@Query("SELECT * FROM flights")
    public List<Flight> findAll();

}
