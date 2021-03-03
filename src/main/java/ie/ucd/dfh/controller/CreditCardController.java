package ie.ucd.dfh.controller;

import ie.ucd.dfh.UserSession;
import ie.ucd.dfh.model.CreditCard;
import ie.ucd.dfh.model.User;
import ie.ucd.dfh.repository.CreditCardRepository;
import ie.ucd.dfh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

            CreditCard creditCard = user.getCreditCard();
            if(creditCard != null){
                creditCard.setCardType(cardType);
                creditCard.setCardNumber(cardNumber);
                creditCard.setExpiryMonth(expiryMonth);
                creditCard.setExpiryYear(expiryYear);
                creditCard.setSecurityCode(securityCode);
            }else{
                creditCard = new CreditCard(cardType, cardNumber, expiryMonth, expiryYear, securityCode);
            }


            user.setCreditCard(creditCard);
            userRepository.save(user);
            response.sendRedirect("/profile?id="+user.getId());
        }else{
            response.sendRedirect("/");
        }
    }
}
