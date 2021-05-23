package ie.ucd.dfh.validator;

import ie.ucd.dfh.model.CreditCard;
import ie.ucd.dfh.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CreditCardValidator implements Validator {

    private static final List<String> CARD_TYPES = Arrays.asList("Visa Credit", "Visa Debit", "Visa Electron", "Mastercard", "Maestro");
    private static final String SECURITY_CODE_PATTERN = "^[0-9]{3,4}$";
    private static final String CREDIT_CARD_NUMBER_PATTERN = "^[0-9]{8,19}$";

    @Override
    public boolean supports(Class<?> clazz) {
        return CreditCard.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CreditCard creditCard = (CreditCard) o;
        DateFormat df = new SimpleDateFormat("yyyy");

        if(!CARD_TYPES.contains(creditCard.getCardType())) {
            errors.rejectValue("cardType", "InvalidCreditCardInfo");
        }
        if(Integer.parseInt(creditCard.getExpiryMonth()) < 1 || Integer.parseInt(creditCard.getExpiryMonth()) > 12) {
            errors.rejectValue("expiryMonth", "InvalidCreditCardInfo");
        }
        if (Integer.parseInt(creditCard.getExpiryYear()) < Integer.parseInt(df.format(Calendar.getInstance().getTime()))) {
            errors.rejectValue("expiryYear", "InvalidCreditCardInfo");
        }
        if(!isValid(creditCard.getCardNumber(), CREDIT_CARD_NUMBER_PATTERN)) {
            errors.rejectValue("cardNumber", "InvalidCreditCardInfo");
        }
        if(!isValid(creditCard.getSecurityCode(), SECURITY_CODE_PATTERN)) {
            errors.rejectValue("securityCode", "InvalidCreditCardInfo");
        }
    }

    private boolean isValid(String toValidate, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(toValidate);
        return matcher.matches();
    }
}
