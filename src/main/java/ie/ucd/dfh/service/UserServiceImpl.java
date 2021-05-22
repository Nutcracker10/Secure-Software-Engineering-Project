package ie.ucd.dfh.service;

import ie.ucd.dfh.model.Attempts;
import ie.ucd.dfh.model.ConfirmationToken;
import ie.ucd.dfh.model.Role;
import ie.ucd.dfh.model.User;
import ie.ucd.dfh.repository.AttemptsRepository;
import ie.ucd.dfh.repository.ConfirmationTokenRepository;
import ie.ucd.dfh.repository.RoleRepository;
import ie.ucd.dfh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AttemptsRepository attemptsRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(userRole);
        user.setRoles(roleSet);
        userRepository.save(user);
    }

    @Override
    public void save(Attempts attempts) {
        attemptsRepository.save(attempts);
    }

    @Override
    public void save(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public Attempts findAttemptsByUsername(String username){
        return attemptsRepository.findAttemptsByUsername(username).orElse(null);
    }
}
