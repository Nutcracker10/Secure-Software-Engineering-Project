package ie.ucd.dfh.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "credit_card")
public class CreditCard {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_card_id")
    private Long creditCardId;

    @NotBlank
    @Column(name = "card_type")
    private String cardType;

    @NotBlank
    @Column(name = "card_number")
    private String cardNumber;

    @NotBlank
    @Column(name = "expiry_month")
    private String expiryMonth;

    @NotBlank
    @Column(name = "expiry_year")
    private String expiryYear;

    @NotBlank
    @Column(name = "security_code")
    private String securityCode;

    public CreditCard() {
    }

    public CreditCard(@NotBlank String cardType, @NotBlank String cardNumber, @NotBlank String expiryMonth, @NotBlank String expiryYear, @NotBlank String securityCode) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.securityCode = securityCode;
    }

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}