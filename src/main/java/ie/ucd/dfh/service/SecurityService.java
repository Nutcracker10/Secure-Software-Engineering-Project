package ie.ucd.dfh.service;

public interface SecurityService {
    String findLoggedInUsername();
    void authenticate(String username, String password);
}