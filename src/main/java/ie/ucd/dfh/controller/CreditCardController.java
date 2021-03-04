package ie.ucd.dfh.controller;

import ie.ucd.dfh.UserSession;
import ie.ucd.dfh.model.CreditCard;
import ie.ucd.dfh.model.User;
import ie.ucd.dfh.repository.CreditCardRepository;
import ie.ucd.dfh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CreditCardController {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSession userSession;

    @PostMapping("/add-credit-card")
    public void addCreditCard(Model model, String cardType, String cardNumber, String expiryMonth, String expiryYear, String securityCode, HttpServletResponse response) throws IOException {

        User user = userRepository.findUserById(userSession.getUser().getId()).orElse(null);

        if(user != null){
            CreditCard creditCard = new CreditCard(cardType, cardNumber, expiryMonth, expiryYear, securityCode, user);
            creditCardRepository.save(creditCard);

            userSession.setUser(user);
            userRepository.save(user);
            response.sendRedirect("/profile?id="+user.getId());
        }else{
            response.sendRedirect("/");
        }
    }

    @DeleteMapping("/credit-card/delete/{id}")
    public void deleteCreditCard(@PathVariable Long id, HttpServletResponse response) throws IOException {
        User user = userSession.getUser();
        if(user != null){
            CreditCard creditCard = creditCardRepository.findById(id).orElse(null);
            if(creditCard != null && creditCard.getCreditCardId().equals(id)){
                user.setCreditCards(null);
                userRepository.save(user);
                creditCardRepository.delete(creditCard);
                response.sendRedirect("/profile?id="+user.getId());
            }else{
                response.sendRedirect("/");
            }
        }else{
            response.sendRedirect("/");
        }
    }
}
