package ie.ucd.dfh.model.wrapper;

import ie.ucd.dfh.model.CreditCard;
import ie.ucd.dfh.model.User;

public class GuestBookFlight {
    private Long flightId;
    private User user = new User();
    private CreditCard creditCard = new CreditCard();

    public GuestBookFlight() {
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public String getFirstName() {
        return this.user.getFirstName();
    }

    public void setFirstName(String firstName) {
        this.user.setFirstName(firstName);
    }

    public String getLastName() {
        return this.user.getLastName();
    }

    public void setLastName(String lastName) {
        this.user.setLastName(lastName);
    }

    public String getAddress() {
        return this.user.getAddress();
    }

    public void setAddress(String address) {
        this.user.setAddress(address);
    }

    public String getPhoneNumber() {
        return this.user.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.user.setPhoneNumber(phoneNumber);
    }

    public String getEmail() {
        return this.user.getEmail();
    }

    public void setEmail(String email) {
        this.user.setEmail(email);
    }

    public String getCardType() {
        return this.creditCard.getCardType();
    }

    public void setCardType(String cardType) {
        this.creditCard.setCardType(cardType);
    }

    public String getCardNumber() {
        return this.creditCard.getCardNumber();
    }

    public void setCardNumber(String cardNumber) {
        this.creditCard.setCardNumber(cardNumber);
    }

    public String getExpiryMonth() {
        return this.creditCard.getExpiryMonth();
    }

    public void setExpiryMonth(String expiryMonth) {
        this.creditCard.setExpiryMonth(expiryMonth);
    }

    public String getExpiryYear() {
        return this.creditCard.getExpiryYear();
    }

    public void setExpiryYear(String expiryYear) {
        this.creditCard.setExpiryYear(expiryYear);
    }

    public String getSecurityCode() {
        return this.creditCard.getSecurityCode();
    }

    public void setSecurityCode(String securityCode) {
        this.creditCard.setSecurityCode(securityCode);
    }
}
