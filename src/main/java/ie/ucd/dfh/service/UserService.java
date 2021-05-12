package ie.ucd.dfh.service;

import ie.ucd.dfh.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
