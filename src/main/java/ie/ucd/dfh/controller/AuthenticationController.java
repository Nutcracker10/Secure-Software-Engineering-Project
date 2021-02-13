package ie.ucd.dfh.controller;

import ie.ucd.dfh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("process-login")
    public void processLogin(){

    }

    @PostMapping("process-registration")
    public void processRegistration(){

    }

}
