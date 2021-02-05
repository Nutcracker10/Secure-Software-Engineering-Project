package ie.ucd.dfh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    String home() {
        return "Spring is here!";
    }
}
