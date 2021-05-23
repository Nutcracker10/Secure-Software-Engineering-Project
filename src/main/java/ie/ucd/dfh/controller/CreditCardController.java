package ie.ucd.dfh.controller;

import ie.ucd.dfh.model.CreditCard;
import ie.ucd.dfh.model.User;
import ie.ucd.dfh.repository.CreditCardRepository;
import ie.ucd.dfh.repository.UserRepository;
import ie.ucd.dfh.service.UserService;
import ie.ucd.dfh.validator.CreditCardValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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

    @Autowired
    private CreditCardValidator creditCardValidator;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/add-credit-card")
    public String addCreditCard(Principal principal, @ModelAttribute("creditCard") @Valid CreditCard creditCard, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        User user = userService.findByUsername(principal.getName());
        if(user != null){
            creditCard.setUser(user);
            creditCardValidator.validate(creditCard, bindingResult);
            if (bindingResult.hasErrors()){
                redirectAttributes.addFlashAttribute("error", "Adding Credit Card was unsuccessful! Make sure you enter all details correctly!");
                return "redirect:/profile/"+principal.getName();
            }

            creditCardRepository.save(creditCard);
            userRepository.save(user);
            log.info(String.format("Credit Card Added: [User ID: %s, username: %s, Credit Card ID: %s]",
                    user.getId(), principal.getName(), creditCard.getCreditCardId()));
            return "redirect:/profile/"+principal.getName();
        }
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/credit-card/delete/{id}")
    public String deleteCreditCard(@PathVariable Long id, Principal principal){
        User user = userService.findByUsername(principal.getName());
        if(user != null) {
            CreditCard creditCard = creditCardRepository.findById(id).orElse(null);
            if (creditCard != null && creditCard.getCreditCardId().equals(id)) {
                user.setCreditCards(null);
                userRepository.save(user);
                creditCardRepository.delete(creditCard);
                log.info(String.format("Credit Card Deleted: [User ID: %s, username: %s, Credit Card ID: %s]",
                        user.getId(), principal.getName(), creditCard.getCreditCardId()));
                return "redirect:/profile/" + principal.getName();
            }
        }
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("credit-card/update")
    public String updateCreditCard(Principal principal, @ModelAttribute("creditCard") @Valid CreditCard creditCard, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        CreditCard existingCreditCard = creditCardRepository.findById(creditCard.getCreditCardId()).orElse(null);
        User user = userService.findByUsername(principal.getName());
        if(existingCreditCard != null && user != null && existingCreditCard.getUser().getId().equals(user.getId())){
            existingCreditCard.setCardType(creditCard.getCardType());
            existingCreditCard.setCardNumber(creditCard.getCardNumber());
            existingCreditCard.setExpiryMonth(creditCard.getExpiryMonth());
            existingCreditCard.setExpiryYear(creditCard.getExpiryYear());
            existingCreditCard.setSecurityCode(creditCard.getSecurityCode());

            creditCardValidator.validate(existingCreditCard, bindingResult);
            if(bindingResult.hasErrors()){
                redirectAttributes.addFlashAttribute("error", "Modification of Credit Card was unsuccessful! Make sure you enter all details correctly!");
                return "redirect:/profile/" + principal.getName();
            }

            creditCardRepository.save(existingCreditCard);
            log.info(String.format("Credit Card Modified: [User ID: %s, username: %s, Credit Card ID: %s]",
                    user.getId(), principal.getName(), existingCreditCard.getCreditCardId()));
            return "redirect:/profile/" + principal.getName();
        }
        return "redirect:/";
    }
}
