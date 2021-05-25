package ie.ucd.dfh.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

    @ManyToOne
    private User user;

    public CreditCard() {
    }

    public CreditCard(@NotBlank String cardType, @NotBlank String cardNumber, @NotBlank String expiryMonth, @NotBlank String expiryYear, @NotBlank String securityCode, User user) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.securityCode = securityCode;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "creditCardId=" + creditCardId +
                ", cardType='" + cardType + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expiryMonth='" + expiryMonth + '\'' +
                ", expiryYear='" + expiryYear + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", user=" + user +
                '}';
    }
}
