package ie.ucd.dfh.service;

import ie.ucd.dfh.model.Attempts;
import ie.ucd.dfh.model.ConfirmationToken;
import ie.ucd.dfh.model.CreditCard;
import ie.ucd.dfh.model.User;

public interface UserService {
    void save(User user);
    void save(Attempts attempts);
    void save(ConfirmationToken confirmationToken);
    void save(CreditCard creditCard);

    User findByUsername(String username);
    User findByEmail(String email);
    Attempts findAttemptsByUsername(String username);

}
