package ie.ucd.dfh.repository;

import ie.ucd.dfh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long id);
    void deleteUserById(Long id);
    User findUserByEmail(String email);
}
