package entities.bills_payment_system;


import javax.persistence.*;


@Entity
@Table(name = "credit_cards")
public class credit_card extends basic_billing_details {

    private String cardType;
    private byte expirationMonth;
    private short expirationYear;

    public credit_card() {
    }
    @Column(name = "type")
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Column(name = "expiration_month")
    public byte getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(byte expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @Column(name = "expiration_year")
    public short getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(short expirationYear) {
        this.expirationYear = expirationYear;
    }
}
