package ie.ucd.dfh.repository;

import ie.ucd.dfh.model.Credentials;
import ie.ucd.dfh.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, Long>{
}
