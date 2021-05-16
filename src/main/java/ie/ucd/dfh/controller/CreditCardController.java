package ie.ucd.dfh.controller;

import ie.ucd.dfh.model.CreditCard;
import ie.ucd.dfh.model.User;
import ie.ucd.dfh.repository.CreditCardRepository;
import ie.ucd.dfh.repository.UserRepository;
import ie.ucd.dfh.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import java.security.Principal;

@Controller
public class CreditCardController {

    private static final Logger log = LoggerFactory.getLogger(CreditCardController.class);

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/add-credit-card")
    public String addCreditCard(Principal principal, String cardType, String cardNumber, String expiryMonth, String expiryYear, String securityCode) {
        User user = userService.findByUsername(principal.getName());

        if(user != null){
            CreditCard creditCard = new CreditCard(cardType, cardNumber, expiryMonth, expiryYear, securityCode, user);
            creditCardRepository.save(creditCard);
            userRepository.save(user);
            log.info("User [ID:"+user.getId()+"] successfully added a new credit card");
            return "redirect:/profile/"+principal.getName();
        }
        return "redirect:/";
    }

    @DeleteMapping("/credit-card/delete/{id}")
    public String deleteCreditCard(@PathVariable Long id, Principal principal){
        User user = userService.findByUsername(principal.getName());
        if(user != null) {
            CreditCard creditCard = creditCardRepository.findById(id).orElse(null);
            if (creditCard != null && creditCard.getCreditCardId().equals(id)) {
                user.setCreditCards(null);
                userRepository.save(user);
                creditCardRepository.delete(creditCard);
                log.info("User [ID:"+user.getId()+"] successfully deleted credit card");
                return "redirect:/profile/" + principal.getName();
            }
        }
        return "redirect:/";
    }

    @PutMapping("credit-card/update")
    public String updateCreditCard(Long creditCardId, Principal principal, String cardType, String cardNumber, String expiryMonth, String expiryYear, String securityCode) {
        CreditCard creditCard = creditCardRepository.findById(creditCardId).orElse(null);
        User user = userService.findByUsername(principal.getName());
        if(creditCard != null && user != null && creditCard.getUser().getId().equals(user.getId())){
            creditCard.setCardType(cardType);
            creditCard.setCardNumber(cardNumber);
            creditCard.setExpiryMonth(expiryMonth);
            creditCard.setExpiryYear(expiryYear);
            creditCard.setSecurityCode(securityCode);
            creditCardRepository.save(creditCard);
            log.info("User [ID:"+user.getId()+"] successfully modified credit card");
            return "redirect:/profile/" + principal.getName();
        }
        return "redirect:/";
    }
}
